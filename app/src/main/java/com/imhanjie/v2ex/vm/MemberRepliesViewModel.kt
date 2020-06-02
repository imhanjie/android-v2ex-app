package com.imhanjie.v2ex.vm

import android.app.Application
import com.imhanjie.v2ex.repository.provideAppRepository

class MemberRepliesViewModel(
    private val userName: String, application: Application
) :
    BasePageViewModel(application) {

    override suspend fun providePageData(requestPage: Int): PageData {
        val result = provideAppRepository().loadMemberReplies(userName, requestPage)
        return PageData(result.replies, result.currentPage != result.totalPage)
    }

}