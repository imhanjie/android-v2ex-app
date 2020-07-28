package com.imhanjie.v2ex.common.extension

import android.os.Looper
import androidx.lifecycle.MutableLiveData

class LiveDataBus private constructor() {

    private val bus = HashMap<String, NonStickyLiveData<Any>>()

    fun with(key: String): MutableLiveData<Any> {
        return with(key, Any::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> with(key: String, type: Class<T>): MutableLiveData<T> {
        if (!bus.containsKey(key)) {
            bus[key] = NonStickyLiveData()
        }
        return bus[key] as NonStickyLiveData<T>
    }

    fun post(key: String, value: Any) {
        if (Looper.myLooper() === Looper.getMainLooper()) {
            with(key).value = value
        } else {
            with(key).postValue(value)
        }
    }

    companion object {
        private val INSTANCE = LiveDataBus()

        /**
         * 获取实例
         */
        fun get() = INSTANCE
    }

}