package com.imhanjie.v2ex.vm

import android.app.Application
import com.imhanjie.support.e
import com.imhanjie.v2ex.repository.provideAppRepository

class PreviewTopicViewModel(application: Application) : BaseViewModel(application) {

    fun previewContent(content: String) = request {
        val result = provideAppRepository().previewTopicContent(content)
        e("preview: $result")
    }

}