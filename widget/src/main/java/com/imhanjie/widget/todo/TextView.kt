package com.imhanjie.widget.todo

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun TextView.setDrawableColor(@ColorRes color: Int) {
    compoundDrawablesRelative.filterNotNull().forEach {
        it.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
    }
}

fun TextView.setDrawableStart(@DrawableRes drawableRes: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(drawableRes, 0, 0, 0)
}

fun TextView.setDrawableTop(@DrawableRes drawableRes: Int) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(0, drawableRes, 0, 0)
}