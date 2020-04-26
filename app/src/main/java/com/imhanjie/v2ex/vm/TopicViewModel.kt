package com.imhanjie.v2ex.vm

import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.common.PAGE_REPLY_COUNT
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.v2ex.repository.provideAppRepository

class TopicViewModel(private val topicId: Long) : BaseViewModel() {

    val loadingState = MutableLiveData<Boolean>()
    val topic = MutableLiveData<Triple<Topic, Boolean, Boolean>>()

    private var currentPage = 1

    init {
        loadReplies(false)
    }

    private fun loadReplies(fromLoadMore: Boolean) {
        request {
            if (!fromLoadMore) loadingState.value = true
            val newTopic = provideAppRepository().loadTopic(topicId, currentPage)
            val hasMore = newTopic.replies.size == PAGE_REPLY_COUNT
            topic.value = Triple(newTopic, fromLoadMore, hasMore)
            if (!fromLoadMore) loadingState.value = false
            currentPage++
        }
    }

    fun loadMore() {
        loadReplies(true)
    }

}