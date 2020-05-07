package com.imhanjie.v2ex.common

import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.imhanjie.support.e

class LiveDataBus private constructor() {

    private val bus = HashMap<String, BusMutableLiveData<Any>>()

    fun with(key: String): MutableLiveData<Any> {
        return with(key, Any::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> with(key: String, type: Class<T>): MutableLiveData<T> {
        if (!bus.containsKey(key)) {
            bus[key] = BusMutableLiveData()
        }
        return bus[key] as BusMutableLiveData<T>
    }

    fun post(key: String, value: Any) {
        if (Looper.myLooper() === Looper.getMainLooper()) {
            e("setValue!")
            with(key).value = value
        } else {
            e("postValue!")
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

    class BusMutableLiveData<T> : MutableLiveData<T>() {

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, observer)
            try {
                hook(observer)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun hook(observer: Observer<in T>) {
            val fieldObservers = LiveData::class.java.getDeclaredField("mObservers")
            fieldObservers.isAccessible = true
            val mObservers = fieldObservers.get(this)

            val methodGet = mObservers.javaClass.getDeclaredMethod("get", Any::class.java)
            methodGet.isAccessible = true
            val objectWrapperEntry = methodGet.invoke(mObservers, observer)
            val objectWrapper = (objectWrapperEntry as Map.Entry<*, *>).value!!
            val classObserverWrapper = objectWrapper.javaClass.superclass!!

            val fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion")
            fieldLastVersion.isAccessible = true
            val fieldVersion = LiveData::class.java.getDeclaredField("mVersion")
            fieldVersion.isAccessible = true
            val objectVersion = fieldVersion.get(this)
            fieldLastVersion.set(objectWrapper, objectVersion)
        }

    }

}