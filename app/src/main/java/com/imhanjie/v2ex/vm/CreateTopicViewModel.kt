package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.App
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.api.model.SearchNode
import com.imhanjie.v2ex.common.valueIsNull

class CreateTopicViewModel(application: Application) : BaseViewModel(application) {

    private val _selectedNode = MutableLiveData<SearchNode>()

    val selectedNode: LiveData<SearchNode>
        get() = _selectedNode

    private val _newTopicId = MutableLiveData<Long>()

    val newTopicId: LiveData<Long>
        get() = _newTopicId

    private var once: String? = null

    init {
        loadCreateTopicInfo()
    }

    private fun loadCreateTopicInfo() = request {
        val result = repo.loadCreateTopicInfo()
        once = result.once
    }

    fun setSelectedNode(selectedNode: SearchNode) {
        _selectedNode.value = selectedNode
    }

    fun createTopic(title: String, content: String) {
        if (once == null) {
            _toast.value = getApplication<App>().getString(R.string.tips_empty_page_once)
            return
        }
        if (title.isEmpty()) {
            _toast.value = getApplication<App>().getString(R.string.hint_empty_topic_title)
            return
        }
        if (_selectedNode.valueIsNull()) {
            _toast.value = getApplication<App>().getString(R.string.plz_choose_node)
            return
        }
        request(withLoading = true) {
            _newTopicId.value = repo.createTopic(title, content, _selectedNode.value!!.id, once!!)
        }
    }


}