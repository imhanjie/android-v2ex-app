package com.imhanjie.v2ex.vm

import android.app.Application
import com.imhanjie.support.e
import com.imhanjie.v2ex.repository.provideAppRepository

class FavoriteTopicsViewModel(application: Application) : BasePageViewModel(application) {

    init {
        e("FavoriteTopicsViewModel 初始化")
    }

    override suspend fun providePageData(requestPage: Int): PageData {
        val result = provideAppRepository().loadFavoriteTopics(requestPage)
        return PageData(result.topics, result.currentPage != result.totalPage)
    }

}