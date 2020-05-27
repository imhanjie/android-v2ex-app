package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.api.model.TopicItem
import com.imhanjie.v2ex.common.TopicTab
import com.imhanjie.v2ex.repository.provideAppRepository

class TabViewModel(application: Application) : BaseViewModel(application) {

    data class TopicLiveDataItem(
        val topics: List<TopicItem>,
        val diff: Boolean
    )


    lateinit var tab: TopicTab

    private val _topic: MutableLiveData<TopicLiveDataItem> = MutableLiveData()

    val topic: LiveData<TopicLiveDataItem>
        get() = _topic

    fun loadTopics() {
        request {
            val topics = provideAppRepository().loadLatestTopics(tab.value, 1)
            _topic.value = TopicLiveDataItem(topics, diff = false)
        }
    }

    fun removeItem(topicId: Long) {
        _topic.value?.let {
            _topic.value = TopicLiveDataItem(
                it.topics.toMutableList().filter { item -> item.id != topicId },
                diff = true
            )
        }
    }

}