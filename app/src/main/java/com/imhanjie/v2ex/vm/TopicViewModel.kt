package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.imhanjie.support.e
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.v2ex.repository.provideAppRepository

class TopicViewModel(private val topicId: Long, application: Application) : BaseViewModel(application) {

    data class TopicLiveData(
        val topic: Topic,
        val append: Boolean,
        val hasMore: Boolean,
        val isOrder: Boolean
    )

    val loadingWrapState = MutableLiveData<Boolean>()
    val topicData = MutableLiveData<TopicLiveData>()
    val loadingDialog = MutableLiveData<Boolean>()
    private var isOrder = true

    private var currentPage = 1
    private var totalPage = -1
    private var isFirst = true

    init {
        loadReplies(append = false, doReverse = false)
    }

    fun loadReplies(append: Boolean, doReverse: Boolean) {
        if (doReverse && totalPage < 0) {
            throw RuntimeException("首次加载不允许逆转评论!")
        }
        e("append: $append, doReverse: $doReverse")
        val targetIsOrder = if (doReverse) !isOrder else isOrder
        if (!append) {
            currentPage = if (targetIsOrder) 1 else totalPage
        }
        request {
            // change state
            if (isFirst) {
                loadingWrapState.value = true
            } else if (!append) {
                loadingDialog.value = true
            }

            var newTopic = provideAppRepository().loadTopic(topicId, currentPage)
            val hasMore = if (targetIsOrder) {
                newTopic.currentPage != newTopic.totalPage
            } else {
                newTopic.currentPage != 1
            }
            if (!targetIsOrder) {
                newTopic = newTopic.copy(replies = newTopic.replies.reversed())
            }
            topicData.value = TopicLiveData(newTopic, append, hasMore, targetIsOrder)

            // change state
            if (isFirst) {
                loadingWrapState.value = false
            } else if (!append) {
                loadingDialog.value = false
            }
            // change value
            totalPage = newTopic.totalPage
            if (targetIsOrder) {
                currentPage++
            } else {
                currentPage--
            }
            isFirst = false
            isOrder = targetIsOrder
        }
    }

}