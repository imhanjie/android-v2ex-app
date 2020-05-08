package com.imhanjie.widget.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class StaticViewPager @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null
) : ViewPager(ctx, attrs) {

    private var enable = false

    init {
        this.enable = false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (this.enable) {
            return super.onTouchEvent(ev)
        }
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (this.enable) {
            return super.onInterceptTouchEvent(ev)
        }
        return false
    }

    fun setPagingEnabled(enable: Boolean) {
        this.enable = enable
    }

}