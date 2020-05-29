package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.api.model.Node
import com.imhanjie.v2ex.repository.provideAppRepository

class NodeViewModel(private val nodeName: String, application: Application) : BasePageViewModel(application) {

    private val _isFavorite = MutableLiveData<Pair<Boolean, Boolean>>()

    val isFavorite: LiveData<Pair<Boolean, Boolean>>
        get() = _isFavorite

    private var node: Node? = null

    override suspend fun providePageData(requestPage: Int): PageData {
        return provideAppRepository().loadNodeTopics(nodeName, requestPage).let {
            node = it
            _isFavorite.value = Pair(it.isFavorite, false)
            PageData(it.topics, it.currentPage != it.totalPage)
        }
    }

    /**
     * 收藏 / 取消收藏主题
     */
    fun doFavoriteNode() = request(withLoading = true) {
        node?.let {
            if (it.isFavorite) {
                provideAppRepository().unFavoriteNode(it.id, it.once)
            } else {
                provideAppRepository().favoriteNode(it.id, it.once)
            }
            _isFavorite.value = Pair(!it.isFavorite, true)
            it.isFavorite = !it.isFavorite
        }
    }

}