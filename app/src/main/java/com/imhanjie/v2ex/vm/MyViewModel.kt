package com.imhanjie.v2ex.vm

import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.model.Topic
import com.imhanjie.v2ex.repository.provideAppRepository

class MyViewModel : BaseViewModel() {

    val topicData: MutableLiveData<List<Topic>> = MutableLiveData()

    init {
        request {
            val topics = provideAppRepository().loadTopics()
            topicData.value = topics
        }

    }

}