package com.imhanjie.v2ex.vm

import android.app.Application
import com.imhanjie.v2ex.api.model.TopicItem
import com.imhanjie.v2ex.common.TopicTab
import com.imhanjie.v2ex.repository.provideAppRepository

class TabViewModel(private val tab: TopicTab, application: Application) : BasePageViewModel(application) {

    override suspend fun providePageData(requestPage: Int): PageData {
        val result = provideAppRepository().loadLatestTopics(tab.value, 1)
        return PageData(result, false)
    }

    /**
     * 移除 topic item
     */
    fun removeTopic(id: Long) = filterItem {
        (it as? TopicItem)?.id != id
    }

}