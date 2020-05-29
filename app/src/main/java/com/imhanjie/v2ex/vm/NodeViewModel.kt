package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NodeViewModel(private val nodeName: String, application: Application) : BaseViewModel(application) {

    private val _isFavorite = MutableLiveData<Pair<Boolean, Boolean>>()

    val isFavorite: LiveData<Pair<Boolean, Boolean>>
        get() = _isFavorite

    /**
     * 收藏 / 取消收藏主题
     */
    fun doFavoriteNode() {
//        val node = _node.value?.node ?: return
//        request(withLoading = true) {
//            if (node.isFavorite) {
//                provideAppRepository().unFavoriteNode(node.id, node.once)
//            } else {
//                provideAppRepository().favoriteNode(node.id, node.once)
//            }
//            _isFavorite.value = Pair(!node.isFavorite, true)
//            node.isFavorite = !node.isFavorite
//        }
    }

}