package com.imhanjie.widget

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.imhanjie.support.ext.dp

class LoadingWrapLayout @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(ctx, attrs, defStyleAttr) {

    init {
        val pb: ProgressBar = ProgressBar(context)
        val params: FrameLayout.LayoutParams = LayoutParams(38f.dp().toInt(), 38f.dp().toInt())
        params.gravity = Gravity.CENTER
        pb.indeterminateDrawable.colorFilter = PorterDuffColorFilter(
            ContextCompat.getColor(context, R.color.widget_loading),
            PorterDuff.Mode.SRC_IN
        )
        addView(pb, params)
    }

    fun show() {
        for (i in 0 until childCount) {
            if (i == 0) {
                getChildAt(i).visibility = View.VISIBLE
            } else {
                getChildAt(i).visibility = View.GONE
            }
        }
    }

    fun hide() {
        for (i in 0 until childCount) {
            if (i == 0) {
                getChildAt(i).visibility = View.GONE
            } else {
                getChildAt(i).visibility = View.VISIBLE
            }
        }
    }

}