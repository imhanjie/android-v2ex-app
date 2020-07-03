package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.model.SearchNode
import com.imhanjie.v2ex.repository.provideAppRepository

class SearchNodeViewModel(application: Application) : BaseViewModel(application) {

    private val _searchNodes = MutableLiveData<List<SearchNode>>()

    val searchNodes: LiveData<List<SearchNode>>
        get() = _searchNodes

    private val allNodes = mutableListOf<SearchNode>()

    init {
        loadAllNode()
    }

    private fun loadAllNode() = request {
        allNodes.clear()
        allNodes.addAll(provideAppRepository().loadAllNodeForSearch())
    }

    fun searchNodes(keyWord: String) {
        _searchNodes.value =
            if (keyWord.isBlank()) {
                emptyList()
            } else {
                allNodes.filter { it.text.contains(keyWord) }
            }
    }

}