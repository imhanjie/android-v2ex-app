package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.imhanjie.v2ex.api.model.Node
import com.imhanjie.v2ex.common.ExtraKeys
import com.imhanjie.v2ex.common.MissingArgumentException

class NodeViewModel(application: Application, savedStateHandle: SavedStateHandle) : BasePageViewModel(application) {

    private val nodeName = savedStateHandle.get<String>(ExtraKeys.NODE_NAME)
        ?: throw MissingArgumentException(ExtraKeys.NODE_NAME)

    private val _isFavorite = MutableLiveData<Pair<Boolean, Boolean>>()

    val isFavorite: LiveData<Pair<Boolean, Boolean>>
        get() = _isFavorite

    private var _node = MutableLiveData<Node>()

    val node: LiveData<Node>
        get() = _node


    override suspend fun providePageData(requestPage: Int): PageData {
        return repo.loadNodeTopics(nodeName, requestPage).let {
            _node.value = it
            _isFavorite.value = Pair(it.isFavorite, false)
            PageData(it.topics, it.currentPage != it.totalPage)
        }
    }

    /**
     * 收藏 / 取消收藏主题
     */
    fun doFavoriteNode() = request(withLoading = true) {
        _node.value?.let {
            if (it.isFavorite) {
                repo.unFavoriteNode(it.id, it.once)
            } else {
                repo.favoriteNode(it.id, it.once)
            }
            _isFavorite.value = Pair(!it.isFavorite, true)
            it.isFavorite = !it.isFavorite
        }
    }

}