package com.imhanjie.v2ex.vm

import android.app.Application
import com.imhanjie.v2ex.repository.provideAppRepository

class MemberTopicsViewModel(
    private val userName: String, application: Application
) :
    BasePageViewModel(application) {

    override suspend fun providePageData(requestPage: Int): PageData {
        val result = provideAppRepository().loadMemberTopics(userName, requestPage)
        return PageData(result.topics, result.currentPage != result.totalPage)
    }

}