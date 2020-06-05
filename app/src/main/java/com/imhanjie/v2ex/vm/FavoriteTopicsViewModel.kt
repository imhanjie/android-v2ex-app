package com.imhanjie.v2ex.vm

import android.app.Application
import com.imhanjie.v2ex.api.model.TopicItem
import com.imhanjie.v2ex.repository.provideAppRepository

class FavoriteTopicsViewModel(application: Application) : BasePageViewModel(application) {

    override suspend fun providePageData(requestPage: Int): PageData {
        val result = provideAppRepository().loadFavoriteTopics(requestPage)
        return PageData(result.topics, result.currentPage != result.totalPage)
    }

    /**
     * 移除 topic item
     */
    fun removeTopic(id: Long) = filterItem {
        (it as? TopicItem)?.id != id
    }

}