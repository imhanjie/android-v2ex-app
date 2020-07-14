package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.App
import com.imhanjie.v2ex.R

class AppendTopicViewModel(private val topicId: Long, application: Application) : BaseViewModel(application) {

    private val _appendResult = MutableLiveData<Boolean>()

    val appendResult: LiveData<Boolean>
        get() = _appendResult

    private var once: String? = null

    init {
        loadAppendTopicInfo()
    }

    private fun loadAppendTopicInfo() = request {
        val result = repo.loadAppendTopicInfo(topicId)
        once = result.once
    }

    fun appendTopic(content: String) {
        if (once == null) {
            _toast.value = getApplication<App>().getString(R.string.tips_empty_page_once)
            return
        }
        if (content.isEmpty()) {
            _toast.value = getApplication<App>().getString(R.string.tips_empty_append_content)
            return
        }
        request(withLoading = true) {
            repo.appendTopic(topicId, content, once!!)
            _appendResult.value = true
        }
    }


}