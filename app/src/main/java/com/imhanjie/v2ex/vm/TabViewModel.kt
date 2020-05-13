package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.common.TopicTab
import com.imhanjie.v2ex.parser.model.TopicItem
import com.imhanjie.v2ex.repository.provideAppRepository

class TabViewModel(application: Application) : BaseViewModel(application) {

    lateinit var tab: TopicTab
    val swipeStateLiveData = MutableLiveData<Boolean>()
    val topicLiveData: MutableLiveData<List<TopicItem>> = MutableLiveData()

    fun loadTopics(fromSwipe: Boolean) {
        request {
            if (fromSwipe) swipeStateLiveData.value = true
            val topics = provideAppRepository().loadLatestTopics(tab.value, 1)
            topicLiveData.value = topics
            if (fromSwipe) swipeStateLiveData.value = false
        }
    }

}