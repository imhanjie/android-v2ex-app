package com.imhanjie.v2ex.vm

import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.common.PAGE_REPLY_COUNT
import com.imhanjie.v2ex.parser.model.Reply
import com.imhanjie.v2ex.repository.provideAppRepository

class TopicViewModel(private val topicId: Long) : BaseViewModel() {

    val loadingState = MutableLiveData<Boolean>()
    val replies = MutableLiveData<Triple<List<Reply>, Boolean, Boolean>>()

    private var currentPage = 1

    init {
        loadReplies(false)
    }

    private fun loadReplies(fromLoadMore: Boolean) {
        request {
            if (!fromLoadMore) loadingState.value = true
            val newReplies = provideAppRepository().loadTopic(topicId, currentPage)
            val hasMore = newReplies.size == PAGE_REPLY_COUNT
            replies.value = Triple(newReplies, fromLoadMore, hasMore)
            if (!fromLoadMore) loadingState.value = false
            currentPage++
        }
    }

    fun loadMore() {
        loadReplies(true)
    }

}