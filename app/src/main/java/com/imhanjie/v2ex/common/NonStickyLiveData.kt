package com.imhanjie.v2ex.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * 一个可以让 Observer 订阅时不立即分发订阅之前的事件的 LiveData，只分发订阅之后的事件
 * - 去除 LiveData 黏性事件
 */
class NonStickyLiveData<T> : MutableLiveData<T>() {

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