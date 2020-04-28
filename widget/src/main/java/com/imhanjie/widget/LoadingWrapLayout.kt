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
        val pb = ProgressBar(context)
        val params = LayoutParams(38f.dp().toInt(), 38f.dp().toInt())
        params.gravity = Gravity.CENTER
        pb.indeterminateDrawable.colorFilter = PorterDuffColorFilter(
            ContextCompat.getColor(context, R.color.widget_loading),
            PorterDuff.Mode.SRC_IN
        )
        addView(pb, params)
    }

    /**
     * 刷新页面
     *
     * @param done 是否已完成，true 隐藏 loading，反之显示 loading
     */
    fun update(done: Boolean) {
        for (i in 0 until childCount) {
            if (i == 0) {
                getChildAt(i).visibility = if (done) View.GONE else View.VISIBLE
            } else {
                getChildAt(i).visibility = if (done) View.VISIBLE else View.GONE
            }
        }
    }

}