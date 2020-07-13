package com.imhanjie.v2ex.vm

import android.app.Application

class NotificationsViewModel(application: Application) : BasePageViewModel(application) {

    override suspend fun providePageData(requestPage: Int): PageData {
        val result = repo.loadNotifications(requestPage)
        return PageData(result.items, result.currentPage != result.totalPage)
    }

}