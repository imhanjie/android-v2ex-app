package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PreviewTopicViewModel(application: Application) : BaseViewModel(application) {

    private val _richContent = MutableLiveData<String>()

    val richContent: LiveData<String>
        get() = _richContent

    fun previewContent(content: String) {
        if (content.isEmpty()) {
            return
        }
        request {
            _richContent.value = repo.previewTopicContent(content)
        }
    }

}