package com.imhanjie.widget.nav

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.imhanjie.widget.R


class BottomNavigationBar @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(ctx, attrs, defStyleAttr) {

    private val items = mutableListOf<Item>()
    private var selectedIndex: Int = 0

    @ColorInt
    private var textColor: Int

    @ColorInt
    private var selectedTextColor: Int

    var onTabClickListener: ((Int, Int) -> Unit)? = null

    init {
        orientation = HORIZONTAL
        textColor = ContextCompat.getColor(context, R.color.widget_text_4)
        selectedTextColor = ContextCompat.getColor(context, R.color.widget_text_1)
    }

    fun addItem(item: Item) {
        items.add(item)
    }

    /**
     * 设置默认选中的 tab
     */
    fun firstSelected(index: Int) {
        selectedIndex = index
    }

    /**
     * 使用代码手动选中 tab，不会触发点击监听
     */
    fun selectTab(index: Int) {
        innerSelectTab(index, false)
    }

    /**
     * 选中 tab
     *
     * @param index           tab 位置
     * @param triggerListener 是否触发点击监听
     */
    private fun innerSelectTab(index: Int, triggerListener: Boolean) {
        val selectedView = getChildAt(selectedIndex)
        changeSelectedStateColor(selectedView.findViewById(R.id.tv_text), false)
        changeSelectedStateColor(selectedView.findViewById(R.id.iv_icon), false)

        val targetView = getChildAt(index)
        changeSelectedStateColor(targetView.findViewById(R.id.tv_text), true)
        changeSelectedStateColor(targetView.findViewById(R.id.iv_icon), true)

        if (triggerListener) {
            onTabClickListener?.invoke(index, selectedIndex)
        }
        selectedIndex = index
    }

    private fun changeSelectedStateColor(view: View, selected: Boolean) {
        val targetColor = if (selected) selectedTextColor else textColor
        when (view) {
            is TextView -> view.setTextColor(targetColor)
            is ImageView -> view.setColorFilter(targetColor, PorterDuff.Mode.SRC_IN)
        }
    }

    /**
     * 初始化
     */
    fun initialise() {
        var textView: TextView
        var imageView: ImageView
        for (i in items.indices) {
            val item = items[i]

            val itemView: View = LayoutInflater.from(context).inflate(R.layout.widget_item_nav, this, false)
            textView = itemView.findViewById(R.id.tv_text)
            textView.apply {
                text = item.text
                changeSelectedStateColor(this, false)
            }

            imageView = itemView.findViewById(R.id.iv_icon)
            imageView.apply {
                setImageResource(item.drawableRes)
                changeSelectedStateColor(this, false)
            }

            val clickIndex: Int = i
            itemView.setOnClickListener {
                if (clickIndex != selectedIndex) {
                    innerSelectTab(clickIndex, true)
                }
            }
            addView(itemView)
        }
        if (selectedIndex >= 0 && selectedIndex < items.size) {
            innerSelectTab(selectedIndex, false)
        }
    }

    data class Item(
        val text: String,
        @DrawableRes val drawableRes: Int
    )

}
