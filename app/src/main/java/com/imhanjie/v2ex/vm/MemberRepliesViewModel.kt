package com.imhanjie.v2ex.vm

import android.app.Application

class MemberRepliesViewModel(
    private val userName: String, application: Application
) :
    BasePageViewModel(application) {

    override suspend fun providePageData(requestPage: Int): PageData {
        val result = repo.loadMemberReplies(userName, requestPage)
        return PageData(result.replies, result.currentPage != result.totalPage)
    }

}