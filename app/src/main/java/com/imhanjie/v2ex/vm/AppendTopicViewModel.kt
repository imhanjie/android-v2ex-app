package com.imhanjie.v2ex.vm

import android.app.Application
import com.imhanjie.support.e
import com.imhanjie.v2ex.App
import com.imhanjie.v2ex.R

class AppendTopicViewModel(private val topicId: Long, application: Application) : BaseViewModel(application) {

    private var once: String? = null

    init {
        loadAppendTopicInfo()
    }

    private fun loadAppendTopicInfo() = request {
        val result = repo.loadAppendTopicInfo(topicId)
        once = result.once
        e("append once is: $once")
    }

    fun appendTopic(content: String) {
        if (once == null) {
            _toast.value = getApplication<App>().getString(R.string.tips_empty_page_once)
            return
        }
    }


}