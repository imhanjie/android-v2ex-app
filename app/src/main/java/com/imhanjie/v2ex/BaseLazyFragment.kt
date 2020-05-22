package com.imhanjie.v2ex

import androidx.viewbinding.ViewBinding

abstract class BaseLazyFragment<VB : ViewBinding> : BaseFragment<VB>() {

    abstract fun onFirstResume()

    protected var isFirstResume = true

    override fun onResume() {
        super.onResume()
        if (isFirstResume) {
            onFirstResume()
            isFirstResume = false
        }
    }

}