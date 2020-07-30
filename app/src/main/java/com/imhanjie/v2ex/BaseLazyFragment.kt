package com.imhanjie.v2ex

import android.os.Bundle
import androidx.viewbinding.ViewBinding

abstract class BaseLazyFragment<VB : ViewBinding> : BaseFragment<VB>() {

    abstract fun onFirstResume()

    protected var isFirstResume = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            isFirstResume = it.getBoolean(IS_FIRST_RESUME, true)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isFirstResume) {
            onFirstResume()
            isFirstResume = false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_FIRST_RESUME, isFirstResume)
    }

    companion object {
        const val IS_FIRST_RESUME = "is_first_resume"
    }

}