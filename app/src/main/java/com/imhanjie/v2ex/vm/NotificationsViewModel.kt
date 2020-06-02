package com.imhanjie.v2ex.vm

import android.app.Application
import com.imhanjie.v2ex.repository.provideAppRepository

class NotificationsViewModel(application: Application) : BasePageViewModel(application) {

    override suspend fun providePageData(requestPage: Int): PageData {
        val result = provideAppRepository().loadNotifications(requestPage)
        return PageData(result.items, result.currentPage != result.totalPage)
    }

}