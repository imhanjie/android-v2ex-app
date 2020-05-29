package com.imhanjie.v2ex.common

import androidx.lifecycle.LiveData

fun <T> LiveData<T>.valueIsNull(): Boolean {
    return this.value == null
}