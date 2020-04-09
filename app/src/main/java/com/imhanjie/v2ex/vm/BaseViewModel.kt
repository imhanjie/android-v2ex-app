package com.imhanjie.v2ex.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel : ViewModel() {

    val error: MutableLiveData<String> = MutableLiveData()

    fun request(
        catchBlock: CoroutineScope.(e: String) -> Unit = { error.value = it },
        finallyBlock: CoroutineScope.() -> Unit = {},
        tryBlock: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            try {
                tryBlock()
            } catch (e: Throwable) {
                catchBlock(handleException(e))
            } finally {
                finallyBlock()
            }
        }
    }

    private fun handleException(e: Throwable): String {
        e.printStackTrace()
        return when (e) {
            is UnknownHostException, is ConnectException -> "网络异常"
            is SocketTimeoutException -> "网络连接超时"
            is IOException -> "请检查你的网络设置"
            else -> e.toString()
        }
    }


}