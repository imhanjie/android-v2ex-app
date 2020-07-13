package com.imhanjie.v2ex.vm

import android.app.Application

class MemberTopicsViewModel(
    private val userName: String, application: Application
) :
    BasePageViewModel(application) {

    override suspend fun providePageData(requestPage: Int): PageData {
        val result = repo.loadMemberTopics(userName, requestPage)
        return PageData(result.topics, result.currentPage != result.totalPage)
    }

}