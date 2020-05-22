package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.parser.model.Node
import com.imhanjie.v2ex.repository.provideAppRepository

class NodeViewModel(private val nodeName: String, application: Application) : BaseViewModel(application) {

    data class NodeLiveData(
        val node: Node,
        val append: Boolean,
        val hasMore: Boolean
    )

    private val nodeLiveData = MutableLiveData<NodeLiveData>()
    fun getNodeLiveData() = nodeLiveData as LiveData<NodeLiveData>

    private val isFavoriteLiveData = MutableLiveData<Pair<Boolean, Boolean>>()
    fun getIsFavoriteLiveData() = isFavoriteLiveData as LiveData<Pair<Boolean, Boolean>>

    private var currentPage = 1

    init {
        loadNodeTopics(append = false)
    }

    fun loadNodeTopics(append: Boolean) {
        request {
            val requestPage = if (!append) 1 else currentPage + 1
            val node = provideAppRepository().loadNodeTopics(nodeName, requestPage)
            isFavoriteLiveData.value = Pair(node.isFavorite, false)
            nodeLiveData.value = NodeLiveData(node, append, node.currentPage != node.totalPage)
            currentPage = requestPage
        }
    }

    /**
     * 收藏 / 取消收藏主题
     */
    fun doFavoriteNode() {
        val node = nodeLiveData.value?.node ?: return
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