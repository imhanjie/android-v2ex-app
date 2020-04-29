package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.v2ex.repository.provideAppRepository

class TopicViewModel(val topicId: Long, application: Application) : BaseViewModel(application) {

    //    var topicId: Long = -1
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
            val hasMore = newTopic.currentPage != newTopic.totalPage
            topic.value = Triple(newTopic, fromLoadMore, hasMore)
            if (!fromLoadMore) loadingState.value = false
            currentPage++
        }
    }

    fun loadMore() {
        loadReplies(true)
    }

}