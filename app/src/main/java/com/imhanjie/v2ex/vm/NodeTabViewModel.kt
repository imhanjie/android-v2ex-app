package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.api.model.TinyNode
import com.imhanjie.v2ex.common.NonStickyLiveData
import com.imhanjie.v2ex.repository.provideAppRepository

class NodeTabViewModel(application: Application) : BaseViewModel(application) {

    private val _nodes = MutableLiveData<List<Any>>()

    val nodes: LiveData<List<Any>>
        get() = _nodes

    private val _loadState = NonStickyLiveData<Boolean>()

    val loadState: LiveData<Boolean>
        get() = _loadState

    fun loadFavoriteNodes() {
        request(onError = {
            _loadState.value = false
        }) {
            val result = mutableListOf<Any>()
            val myNodes = provideAppRepository().loadFavoriteNodes()
            if (myNodes.isNotEmpty()) {
                result.add("我的收藏")
                result.addAll(myNodes.map {
                    TinyNode(it.title, it.name)
                })
            }
            for (navNode in provideAppRepository().loadNavNodes()) {
                result.add(navNode.type)
                result.addAll(navNode.children)
            }
            _nodes.value = result
            _loadState.value = true
        }
    }

}