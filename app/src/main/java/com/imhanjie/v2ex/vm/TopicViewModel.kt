package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.api.model.Reply
import com.imhanjie.v2ex.api.model.Topic
import com.imhanjie.v2ex.repository.provideAppRepository

class TopicViewModel(val topicId: Long, application: Application) : BaseViewModel(application) {

    data class TopicLiveData(
        val topic: Topic,
        val append: Boolean,
        val hasMore: Boolean,
        val isOrder: Boolean
    )

    private val _topic = MutableLiveData<TopicLiveData>()

    val topic: LiveData<TopicLiveData>
        get() = _topic

    private val _loading = MutableLiveData<Boolean>()

    val loading: LiveData<Boolean>
        get() = _loading

    private val _thankReply = MutableLiveData<Reply>()

    val thankReply: LiveData<Reply>
        get() = _thankReply

    private val _ignoreTopicState = MutableLiveData<Boolean>()

    val ignoreTopicState: LiveData<Boolean>
        get() = _ignoreTopicState


    private var isOrder = true
    private var currentPage = 1
    private var totalPage = -1
    private var isFirst = true
    private var once = ""

    init {
        loadReplies(append = false, doReverse = false)
    }

    /**
     * 获取回复
     *
     * @param append 是否继续分页获取，true 继续分页获取，false 重新获取。
     * @param doReverse 是否反转评论结果。
     */
    fun loadReplies(append: Boolean, doReverse: Boolean) {
        if (doReverse && totalPage < 0) {
            throw RuntimeException("首次加载不允许逆转评论!")
        }

        val targetIsOrder = if (doReverse) !isOrder else isOrder

        if (!append && doReverse && totalPage == 1) {
            // 若只有一页数据，直接翻转评论
            val currentTopic = _topic.value!!.topic
            _topic.value = TopicLiveData(
                currentTopic.copy(replies = currentTopic.replies.reversed()),
                append = false,
                hasMore = false,
                isOrder = targetIsOrder
            )
            isOrder = targetIsOrder
            return
        }

        if (!append) {
            currentPage = if (targetIsOrder) 1 else totalPage
        }
        request {
            // change state
            if (!isFirst && !append) {
                _loading.value = true
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
            _topic.value = TopicLiveData(newTopic, append, hasMore, targetIsOrder)
            once = newTopic.once

            // change state
            if (!isFirst && !append) {
                _loading.value = false
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

    /**
     * 感谢回复
     */
    fun thankReply(reply: Reply) {
        request(withLoading = true) {
            val result = provideAppRepository().thankReply(reply.id, once)
            once = result.once
            if (result.success) {
                reply.thanked = true
                reply.thankCount++
                _thankReply.value = reply
            } else {
                _toast.value = result.message
            }
        }
    }

    /**
     * 忽略主题
     */
    fun ignoreTopic() {
        request(withLoading = true) {
            provideAppRepository().ignoreTopic(topicId, once)
            _ignoreTopicState.value = true
        }
    }

}