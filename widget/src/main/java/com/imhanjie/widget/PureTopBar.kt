package com.imhanjie.widget

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.imhanjie.widget.databinding.WidgetTopBarBinding

class PureTopBar @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(ctx, attrs, defStyleAttr) {

    private val view: WidgetTopBarBinding = WidgetTopBarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        readAttrs(attrs)
        setDefaultEvents()
    }

    private fun readAttrs(attrs: AttributeSet?) {
        attrs?.let {
            context.withStyledAttributes(attrs, R.styleable.WidgetPureTopBar) {
                // title
                view.titleTv.text = getString(R.styleable.WidgetPureTopBar_widget_bar_titleText)
                // right
                view.rightTv.text = getString(R.styleable.WidgetPureTopBar_widget_bar_rightText)
                val rightDrawable = getDrawable(R.styleable.WidgetPureTopBar_widget_bar_rightIcon)
                view.rightTv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, rightDrawable, null);
                view.rightTv.setTextColor(getColor(R.styleable.WidgetPureTopBar_widget_bar_rightTextColor, Color.BLACK))
                view.rightTv.visibility = getInt(R.styleable.WidgetPureTopBar_widget_bar_rightVisibility, View.VISIBLE)
                // right secondary
                view.rightSecondaryTv.text = getString(R.styleable.WidgetPureTopBar_widget_bar_rightSecondaryText)
                val rightSecondaryDrawable = getDrawable(R.styleable.WidgetPureTopBar_widget_bar_rightSecondaryIcon)
                view.rightSecondaryTv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, rightSecondaryDrawable, null)
                view.rightSecondaryTv.setTextColor(getColor(R.styleable.WidgetPureTopBar_widget_bar_rightSecondaryTextColor, Color.BLACK))
                view.rightSecondaryTv.visibility = getInt(R.styleable.WidgetPureTopBar_widget_bar_rightSecondaryVisibility, View.VISIBLE)
                // left
                view.leftIv.visibility = getInt(R.styleable.WidgetPureTopBar_widget_bar_leftVisibility, View.VISIBLE)
                // light mode
                val isLightMode = getBoolean(R.styleable.WidgetPureTopBar_widget_bar_light_mode, false)
                if (isLightMode) {
                    view.titleTv.setTextColor(ContextCompat.getColor(context, R.color.widget_color_text_black_1))
                    view.leftIv.setColorFilter(ContextCompat.getColor(context, R.color.widget_color_text_black_1));
                }
            }
        }
    }

    private fun setDefaultEvents() {
        setOnLeftClickListener(OnClickListener {
            if (context is Activity) {
                (context as Activity).onBackPressed()
            }
        })
    }

    fun setOnRightClickListener(listener: OnClickListener?) {
        view.rightTv.setOnClickListener(listener)
    }

    fun setOnRightSecondaryClickListener(listener: OnClickListener?) {
        view.rightSecondaryTv.setOnClickListener(listener)
    }

    fun setOnLeftClickListener(listener: OnClickListener?) {
        view.leftIv.setOnClickListener(listener)
    }

    fun setTitleText(s: String?) {
        view.titleTv.text = s
    }

    fun setTitleText(@StringRes resId: Int) {
        view.titleTv.setText(resId)
    }

    fun setRightVisibility(visibility: Int) {
        view.rightTv.visibility = visibility
    }

    fun setTitleStartIcon(@DrawableRes iconRes: Int) {
        if (iconRes != 0) {
            view.titleStartIv.visibility = View.VISIBLE
            view.titleStartIv.setImageResource(iconRes)
        } else {
            view.titleStartIv.setVisibility(View.GONE)
        }
    }

}