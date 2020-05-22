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

    private val _node = MutableLiveData<NodeLiveData>()

    val node: LiveData<NodeLiveData>
        get() = _node

    private val _isFavorite = MutableLiveData<Pair<Boolean, Boolean>>()

    val isFavorite: LiveData<Pair<Boolean, Boolean>>
        get() = _isFavorite

    private var currentPage = 1

    init {
        loadNodeTopics(append = false)
    }

    fun loadNodeTopics(append: Boolean) {
        request {
            val requestPage = if (!append) 1 else currentPage + 1
            val node = provideAppRepository().loadNodeTopics(nodeName, requestPage)
            _isFavorite.value = Pair(node.isFavorite, false)
            _node.value = NodeLiveData(node, append, node.currentPage != node.totalPage)
            currentPage = requestPage
        }
    }

    /**
     * 收藏 / 取消收藏主题
     */
    fun doFavoriteNode() {
        val node = _node.value?.node ?: return
        request(withLoading = true) {
            if (node.isFavorite) {
                provideAppRepository().unFavoriteNode(node.id, node.once)
            } else {
                provideAppRepository().favoriteNode(node.id, node.once)
            }
            _isFavorite.value = Pair(!node.isFavorite, true)
            node.isFavorite = !node.isFavorite
        }
    }

}