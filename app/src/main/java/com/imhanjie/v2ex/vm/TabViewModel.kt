package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.common.TopicTab
import com.imhanjie.v2ex.parser.model.TopicItem
import com.imhanjie.v2ex.repository.provideAppRepository

class TabViewModel(application: Application) : BaseViewModel(application) {

    lateinit var tab: TopicTab
    val topicLiveData: MutableLiveData<List<TopicItem>> = MutableLiveData()

    fun loadTopics() {
        request {
            topicLiveData.value = provideAppRepository().loadLatestTopics(tab.value, 1)
        }
    }

}