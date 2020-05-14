package com.imhanjie.widget.todo

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun TextView.setDrawableColor(@ColorRes color: Int) {
    compoundDrawablesRelative.filterNotNull().forEach {
        it.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
    }
}