package com.imhanjie.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

open class LineDividerItemDecoration @JvmOverloads constructor(
    ctx: Context,
    @ColorInt color: Int = ContextCompat.getColor(ctx, R.color.widget_divider),
    private val height: Int = 1,
    private val marginStart: Int = 0,
    private val marginEnd: Int = 0,
    @ColorInt backgroundColor: Int = Color.TRANSPARENT
) : RecyclerView.ItemDecoration() {

    private val divider: Drawable
    private val background: Drawable

    init {
        divider = createDrawable(ctx, color)
        background = createDrawable(ctx, backgroundColor)
    }

    /**
     * 是否跳过绘制，子类可以重写该方法
     *
     * @param position 位置
     */
    open fun isSkip(position: Int): Boolean {
        return false
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left: Int = parent.paddingLeft + marginStart
        val right: Int = parent.width - parent.paddingRight - marginEnd
        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            if (child.visibility == View.GONE) {
                continue
            }
            val pos = parent.getChildAdapterPosition(child)
            if (pos == -1) {
                continue
            }
            if (isSkip(pos)) {
                continue
            }
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom: Int = top + height
            background.setBounds(0, top, parent.width, bottom)
            background.draw(c)
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val pos = parent.getChildAdapterPosition(view)
        if (isSkip(pos)) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(0, 0, 0, height);
        }
    }


    private fun createDrawable(context: Context, dividerColor: Int): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setColor(dividerColor)
        return drawable
    }


}