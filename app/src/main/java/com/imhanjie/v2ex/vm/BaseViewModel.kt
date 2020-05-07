package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.stream.MalformedJsonException
import com.imhanjie.v2ex.common.BizException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val error: MutableLiveData<String> = MutableLiveData()
    val toast: MutableLiveData<String> = MutableLiveData()

    fun request(
        onError: CoroutineScope.(e: String) -> Unit = { },
        onComplete: CoroutineScope.() -> Unit = {},
        onRequest: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            try {
                onRequest()
            } catch (e: Throwable) {
                val msg = handleException(e)
                error.value = msg
                onError(msg)
            } finally {
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