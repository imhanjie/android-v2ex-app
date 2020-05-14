package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.imhanjie.support.e
import com.imhanjie.v2ex.parser.model.Node
import com.imhanjie.v2ex.repository.provideAppRepository

class NodeViewModel(private val nodeName: String, application: Application) : BaseViewModel(application) {

    private var currentPage = 1

    val nodeLiveData = MutableLiveData<Node>()
    val isFavoriteLiveData = MutableLiveData<Pair<Boolean, Boolean>>()

    init {
        loadNodeTopics()
    }

    private fun loadNodeTopics() {
        request {
            val node = provideAppRepository().loadNodeTopics(nodeName, currentPage)
            isFavoriteLiveData.value = Pair(node.isFavorite, false)
            nodeLiveData.value = node
        }
    }

    /**
     * 收藏 / 取消收藏主题
     */
    fun doFavoriteNode() {
        val node = nodeLiveData.value ?: return
        e("doFavoriteNode()")
        request(withLoading = true) {
            if (node.isFavorite) {
                provideAppRepository().unFavoriteNode(node.id, node.once)
            } else {
                provideAppRepository().favoriteNode(node.id, node.once)
            }
            isFavoriteLiveData.value = Pair(!node.isFavorite, true)
            node.isFavorite = !node.isFavorite
        }
    }

}