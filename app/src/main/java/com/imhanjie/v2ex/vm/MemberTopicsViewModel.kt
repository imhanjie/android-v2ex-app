package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.imhanjie.v2ex.common.ExtraKeys
import com.imhanjie.v2ex.common.exception.MissingArgumentException

class MemberTopicsViewModel(application: Application, savedStateHandle: SavedStateHandle) : BasePageViewModel(application) {

    private val userName = savedStateHandle.get<String>(ExtraKeys.USER_NAME)
        ?: throw MissingArgumentException(ExtraKeys.USER_NAME)

    override suspend fun providePageData(requestPage: Int): PageData {
        val result = repo.loadMemberTopics(userName, requestPage)
        return PageData(result.topics, result.currentPage != result.totalPage)
    }

}