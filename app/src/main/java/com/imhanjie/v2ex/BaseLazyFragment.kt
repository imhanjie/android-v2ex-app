package com.imhanjie.v2ex

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding

abstract class BaseLazyFragment<VB : ViewBinding> : BaseFragment<VB>() {

    private var mIsInitView = false
    private var mIsVisible = false
    private var mIsLazyLoaded = false

    abstract fun onLazyLoad()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsInitView = true
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mIsVisible = isVisibleToUser
        if (mIsVisible && mIsInitView) {
            if (mIsLazyLoaded) {
                onResumeAfterLazyLoad()
            } else {
                lazyLoad()
            }
        }
    }

    open fun onResumeAfterLazyLoad() {

    }

    override fun onResume() {
        super.onResume()
        // 解决第一个 Fragment 的 onLazyLoad 不调用的问题
        if (mIsVisible && mIsInitView && !mIsLazyLoaded) {
            lazyLoad()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mIsInitView = false
    }

    private fun lazyLoad() {
        onLazyLoad()
        mIsLazyLoaded = true
    }

}