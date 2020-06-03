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
import com.imhanjie.support.ext.dpi
import com.imhanjie.widget.todo.setDrawableColor
import com.imhanjie.widget.todo.setDrawableTop

class LoadingWrapLayout @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(ctx, attrs, defStyleAttr) {

    var retryCallback: (() -> Unit)? = null

    enum class Status {
        LOADING, DONE, FAIL, EMPTY
    }

    init {
        val pb = ProgressBar(context)
        val pbParams = LayoutParams(38.dpi, 38.dpi)
        pbParams.gravity = Gravity.CENTER
        pb.indeterminateDrawable.colorFilter = PorterDuffColorFilter(
            ContextCompat.getColor(context, R.color.widget_loading),
            PorterDuff.Mode.SRC_IN
        )
        addView(pb, pbParams)

        val errorTextView = TextView(context).apply {
            setTextColor(ContextCompat.getColor(context, R.color.widget_text_4))
            text = "这里什么都没有哦"
            textSize = 13f
            gravity = Gravity.CENTER_HORIZONTAL
            compoundDrawablePadding = 16.dpi
        }
        errorTextView.visibility = View.GONE
        val tvParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.CENTER
        }
        val padding = 16.dpi
        errorTextView.setPadding(padding, padding, padding, padding)
        errorTextView.setOnClickListener {
            retryCallback?.invoke()
        }
        addView(errorTextView, tvParams)
    }

    /**
     * 刷新页面
     */
    fun update(status: Status, tipText: String? = null) {
        when (status) {
            Status.LOADING -> {
                showView(0)
            }
            Status.FAIL, Status.EMPTY -> {
                showView(1)
                val tv = (getChildAt(1) as TextView)
                if (tipText == null) {
                    tv.text = if (status == Status.FAIL) "加载出现了点问题" else "这里什么都没有哦~"
                } else {
                    tv.text = tipText
                }
                tv.setDrawableTop(if (status == Status.FAIL) R.drawable.widget_ic_load_list_fail else R.drawable.widget_ic_load_list_empty)
                tv.setDrawableColor(R.color.widget_text_4)
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