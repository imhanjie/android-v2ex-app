package com.imhanjie.v2ex.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> ViewModelProvider(owner: ViewModelStoreOwner, key: String? = null, crossinline block: () -> T): T {
    val provider = ViewModelProvider(owner, object : ViewModelProvider.Factory {
        override fun <R : ViewModel?> create(modelClass: Class<R>): R {
            return block.invoke() as R
        }
    })
    return if (key == null) {
        provider.get(T::class.java)
    } else {
        provider.get(key, T::class.java)
    }
}