package com.imhanjie.v2ex.vm

import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.parser.model.TopicItem
import com.imhanjie.v2ex.repository.provideAppRepository

class MyViewModel : BaseViewModel() {

    val topicData: MutableLiveData<List<TopicItem>> = MutableLiveData()

    init {
        request {
            val topics = provideAppRepository().loadNodeTopics("apple", 1)
//            val topics = provideAppRepository().loadLatestTopics(2)
            topicData.value = topics
        }

    }

}