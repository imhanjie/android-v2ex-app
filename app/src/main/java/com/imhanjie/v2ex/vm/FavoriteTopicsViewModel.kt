package com.imhanjie.v2ex.vm

import android.app.Application
import com.imhanjie.v2ex.repository.provideAppRepository

class FavoriteTopicsViewModel(application: Application) : BasePageViewModel(application) {

    override suspend fun provideData(requestPage: Int): PageLiveData {
        val result = provideAppRepository().loadFavoriteTopics(requestPage)
        return PageLiveData(result.topics, result.currentPage != result.totalPage)
    }

}