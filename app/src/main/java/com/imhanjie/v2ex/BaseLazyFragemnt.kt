package com.imhanjie.v2ex

import androidx.viewbinding.ViewBinding

abstract class BaseLazyFragemnt<VB : ViewBinding> : BaseFragment<VB>() {

    abstract fun onFirstResume()

    private var isFirstResume = true

    override fun onResume() {
        super.onResume()
        if (isFirstResume) {
            onFirstResume()
            isFirstResume = false
        }
    }

}