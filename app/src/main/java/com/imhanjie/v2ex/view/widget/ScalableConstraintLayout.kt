package com.imhanjie.v2ex.view.widget

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout

class ScalableConstraintLayout @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attrs, defStyleAttr) {

    init {
        isClickable = true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                ValueAnimator.ofFloat(scaleX, 0.96f).apply {
                    interpolator = AccelerateDecelerateInterpolator()
                    addUpdateListener { animation: ValueAnimator ->
                        val value = animation.animatedValue as Float
                        scaleX = value
                        scaleY = value
                    }
                    start()
                }
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                ValueAnimator.ofFloat(scaleX, 1.0f).apply {
                    addUpdateListener { animation: ValueAnimator ->
                        val value = animation.animatedValue as Float
                        scaleX = value
                        scaleY = value
                    }
                    start()
                }
            }
        }
        return super.onTouchEvent(event)
    }

}