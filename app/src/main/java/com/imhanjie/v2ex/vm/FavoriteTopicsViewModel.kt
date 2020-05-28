package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.api.model.TopicItem
import com.imhanjie.v2ex.repository.provideAppRepository

class FavoriteTopicsViewModel(application: Application) : BaseViewModel(application) {

    data class FavoriteTopicsLiveData(
        val topics: List<TopicItem>,
        val append: Boolean,
        val hasMore: Boolean,
        val diff: Boolean = false
    )

    private val _topics = MutableLiveData<FavoriteTopicsLiveData>()

    val topics: LiveData<FavoriteTopicsLiveData>
        get() = _topics

    private var currentPage = 1

    init {
        loadFavoriteTopics(append = false)
    }

    fun loadFavoriteTopics(append: Boolean) {
        request {
            val requestPage = if (!append) 1 else currentPage + 1
            val result = provideAppRepository().loadFavoriteTopics(requestPage)
            _topics.value = FavoriteTopicsLiveData(result.topics, append, result.currentPage != result.totalPage)
            currentPage = requestPage
        }
    }

    fun removeItem(topicId: Long) {
        _topics.value?.let {
            _topics.value = it.copy(
                topics = it.topics.toMutableList().filter { item -> item.id != topicId },
                diff = true
            )
        }
    }

}