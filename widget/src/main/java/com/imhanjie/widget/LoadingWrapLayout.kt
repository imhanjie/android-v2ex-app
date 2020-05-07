package com.imhanjie.widget

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.imhanjie.support.ext.dp

class LoadingWrapLayout @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(ctx, attrs, defStyleAttr) {

    var retryCallback: (() -> Unit)? = null

    enum class Status {
        LOADING, DONE, FAIL
    }

    init {
        val pb = ProgressBar(context)
        val pbParams = LayoutParams(38f.dp().toInt(), 38f.dp().toInt())
        pbParams.gravity = Gravity.CENTER
        pb.indeterminateDrawable.colorFilter = PorterDuffColorFilter(
            ContextCompat.getColor(context, R.color.widget_loading),
            PorterDuff.Mode.SRC_IN
        )
        addView(pb, pbParams)

        val errorTextView = TextView(context).apply {
            setTextColor(ContextCompat.getColor(context, R.color.widget_text_3))
            text = "暂无数据"
            textSize = 13f
        }
        val tvParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        errorTextView.gravity = Gravity.CENTER
        errorTextView.setOnClickListener { retryCallback?.invoke() }
        addView(errorTextView, tvParams)
    }

    /**
     * 刷新页面
     */
    fun update(status: Status, errorText: String = "加载失败，点击重试") {
        when (status) {
            Status.LOADING -> {
                showView(0)
            }
            Status.FAIL -> {
                showView(1)
                (getChildAt(1) as TextView).text = errorText
            }
            Status.DONE -> {
                showView(2)
            }
        }
    }

    private fun showView(index: Int) {
        for (i in 0 until childCount) {
            if (i == index) {
                getChildAt(i).visibility = View.VISIBLE
            } else {
                getChildAt(i).visibility = View.GONE
            }
        }
    }

}