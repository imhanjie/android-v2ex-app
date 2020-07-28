package com.imhanjie.v2ex.common.extension

import androidx.lifecycle.LiveData

fun <T> LiveData<T>.valueIsNull(): Boolean {
    return this.value == null
}