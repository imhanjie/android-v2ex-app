package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.api.model.TopicItem
import com.imhanjie.v2ex.common.TopicTab
import com.imhanjie.v2ex.repository.provideAppRepository

class TabViewModel(application: Application) : BaseViewModel(application) {

    lateinit var tab: TopicTab

    private val _topic: MutableLiveData<List<TopicItem>> = MutableLiveData()

    val topic: LiveData<List<TopicItem>>
        get() = _topic

    fun loadTopics() {
        request {
            _topic.value = provideAppRepository().loadLatestTopics(tab.value, 1)
        }
    }

}