package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.imhanjie.v2ex.api.model.Reply
import com.imhanjie.v2ex.api.model.Topic
import com.imhanjie.v2ex.common.ExtraKeys
import com.imhanjie.v2ex.common.exception.MissingArgumentException
import com.imhanjie.v2ex.common.extension.NonStickyLiveData
import com.imhanjie.v2ex.model.VMEvent

class TopicViewModel(application: Application, savedStateHandle: SavedStateHandle) : BaseViewModel(application) {

    val topicId = savedStateHandle.get<Long>(ExtraKeys.TOPIC_ID)
        ?: throw MissingArgumentException(ExtraKeys.TOPIC_ID)

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

    private val _loadingLayout = MutableLiveData<Boolean>()

    val loadingLayout: LiveData<Boolean>
        get() = _loadingLayout

    private val _thankReply = NonStickyLiveData<Reply>()

    val thankReply: LiveData<Reply>
        get() = _thankReply

    private val _ignoreTopicState = NonStickyLiveData<Boolean>()

    val ignoreTopicState: LiveData<Boolean>
        get() = _ignoreTopicState

    private val _favoriteState = NonStickyLiveData<Boolean>()

    val favoriteState: LiveData<Boolean>
        get() = _favoriteState

    private val _unFavoriteState = NonStickyLiveData<Boolean>()

    val unFavoriteState: LiveData<Boolean>
        get() = _unFavoriteState

    private var isOrder = true
    private var currentPage = 1
    private var totalPage = -1
    private var isFirst = true

    init {
        reloadReplies()
    }

    fun reloadReplies() {
        isFirst = true
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
            if (isFirst) {
                _loadingLayout.value = true
            } else if (!append) {
                _loading.value = true
            }

            var newTopic = repo.loadTopic(topicId, currentPage)
            val hasMore = if (targetIsOrder) {
                newTopic.currentPage != newTopic.totalPage
            } else {
                newTopic.currentPage != 1
            }
            if (!targetIsOrder) {
                newTopic = newTopic.copy(replies = newTopic.replies.reversed())
            }
            _topic.value = TopicLiveData(newTopic, append, hasMore, targetIsOrder)

            // change state
            if (isFirst) {
                _loadingLayout.value = false
            } else if (!append) {
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
            val topic = _topic.value!!.topic
            val result = repo.thankReply(reply.id, topic.once)
            topic.once = result.once
            if (result.success) {
                reply.thanked = true
                reply.thankCount++
                _thankReply.value = reply
            } else {
                _event.value = VMEvent(VMEvent.Event.TOAST, result.message)
            }
        }
    }

    /**
     * 忽略主题
     */
    fun ignoreTopic() {
        request(withLoading = true) {
            val topic = _topic.value!!.topic
            repo.ignoreTopic(topicId, topic.once)
            _ignoreTopicState.value = true
        }
    }

    /**
     * 收藏主题
     */
    fun favoriteTopic() {
        request(withLoading = true) {
            val topic = _topic.value!!.topic
            val result = repo.favoriteTopic(topicId, topic.favoriteParam)
            topic.favoriteParam = result.favoriteParam
            if (result.isFavorite) {
                // 收藏成功
                topic.isFavorite = true
                _favoriteState.value = true
            } else {
                // 收藏失败
                _favoriteState.value = false
            }
        }
    }

    /**
     * 取消收藏主题
     */
    fun unFavoriteTopic() {
        request(withLoading = true) {
            val topic = _topic.value!!.topic
            val result = repo.unFavoriteTopic(topicId, topic.favoriteParam)
            topic.favoriteParam = result.favoriteParam
            if (!result.isFavorite) {
                // 取消收藏成功
                topic.isFavorite = false
                _unFavoriteState.value = true
            } else {
                // 取消收藏失败
                _unFavoriteState.value = false
            }
        }
    }

    /**
     * 是否已收藏主题
     */
    fun topicIsFavorite(): Boolean {
        return _topic.value?.topic?.isFavorite == true
    }

    /**
     * 是否可以 APPEND 主题
     */
    fun canAppendTopic(): Boolean {
        return _topic.value?.topic?.canAppend == true
    }

}