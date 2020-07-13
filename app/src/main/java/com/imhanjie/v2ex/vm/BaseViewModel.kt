package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.stream.MalformedJsonException
import com.imhanjie.v2ex.common.BizException
import com.imhanjie.v2ex.common.SingleLiveEvent
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

    private val _error = SingleLiveEvent<String>()

    val error: LiveData<String>
        get() = _error

    protected val _toast = SingleLiveEvent<String>()

    val toast: LiveData<String>
        get() = _toast

    private val _loadingDialogState = SingleLiveEvent<Boolean>()

    val loadingDialogState: LiveData<Boolean>
        get() = _loadingDialogState

    fun request(
        withLoading: Boolean = false,
        onError: CoroutineScope.(e: String) -> Unit = { },
        onComplete: CoroutineScope.() -> Unit = {},
        onRequest: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (withLoading) {
                    _loadingDialogState.value = true
                }
                onRequest()
            } catch (e: Throwable) {
                val msg = handleException(e)
                _error.value = msg
                onError(msg)
            } finally {
                if (withLoading) {
                    _loadingDialogState.value = false
                }
                onComplete()
            }
        }
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