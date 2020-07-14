package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.imhanjie.v2ex.App
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.common.ExtraKeys
import com.imhanjie.v2ex.common.MissingArgumentException

class AppendTopicViewModel(application: Application, savedStateHandle: SavedStateHandle) : BaseViewModel(application) {

    private val topicId: Long = savedStateHandle.get(ExtraKeys.TOPIC_ID)
        ?: throw MissingArgumentException(ExtraKeys.TOPIC_ID)

    private var once: String? = null

    private val _appendResult = MutableLiveData<Boolean>()

    val appendResult: LiveData<Boolean>
        get() = _appendResult

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