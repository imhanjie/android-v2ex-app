package com.imhanjie.v2ex.vm

import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.parser.model.Reply
import com.imhanjie.v2ex.repository.provideAppRepository

class TopicViewModel(private val topicId: Long) : BaseViewModel() {

    val loadingState = MutableLiveData<Boolean>()
    val replies = MutableLiveData<List<Reply>>()

    init {
        loadReplies()
    }

    private fun loadReplies() {
        request {
            loadingState.value = true
//            replies.value = provideAppRepository().loadTopic(567112)
            replies.value = provideAppRepository().loadTopic(topicId)
            loadingState.value = false
        }
    }

}