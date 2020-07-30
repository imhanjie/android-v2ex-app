package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.stream.MalformedJsonException
import com.imhanjie.v2ex.App
import com.imhanjie.v2ex.common.exception.BizException
import com.imhanjie.v2ex.common.extension.SingleLiveEvent
import com.imhanjie.v2ex.model.VmEvent
import com.imhanjie.v2ex.repository.AppRepository
import com.imhanjie.v2ex.repository.provideAppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected val repo: AppRepository by lazy { provideAppRepository() }

    protected val _event = SingleLiveEvent<VmEvent>()

    val event: LiveData<VmEvent>
        get() = _event

    fun request(
        withLoading: Boolean = false,
        onError: CoroutineScope.(e: String) -> Unit = { },
        onComplete: CoroutineScope.() -> Unit = {},
        onRequest: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (withLoading) {
                    _event.value = VmEvent(VmEvent.Event.SHOW_LOADING)
                }
                onRequest()
            } catch (e: Throwable) {
                val msg = handleException(e)
                _event.value = VmEvent(VmEvent.Event.ERROR, msg)
                onError(msg)
            } finally {
                if (withLoading) {
                    _event.value = VmEvent(VmEvent.Event.HIDE_LOADING)
                }
                onComplete()
            }
        }
    }

    fun getResString(@StringRes resId: Int): String {
        return getApplication<App>().getString(resId)
    }

    private fun handleException(e: Throwable): String {
        e.printStackTrace()
        return when (e) {
            is BizException -> e.message!!
            is MalformedJsonException -> "JSON 数据格式错误"
            is UnknownHostException, is ConnectException -> "网络异常"
            is SocketTimeoutException -> "网络连接超时"
            is IOException -> "请检查你的网络设置"
            else -> e.toString()
        }
    }


}