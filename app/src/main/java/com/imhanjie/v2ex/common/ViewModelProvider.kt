package com.imhanjie.v2ex.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> ViewModelProvider(owner: ViewModelStoreOwner, crossinline block: () -> T): T {
    return ViewModelProvider(owner, object : ViewModelProvider.Factory {
        override fun <R : ViewModel?> create(modelClass: Class<R>): R {
            return block.invoke() as R
        }
    }).get(T::class.java)
}