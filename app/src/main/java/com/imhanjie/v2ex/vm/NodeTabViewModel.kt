package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.parser.model.TinyNode
import com.imhanjie.v2ex.repository.provideAppRepository

class NodeTabViewModel(application: Application) : BaseViewModel(application) {

    val nodesLiveData = MutableLiveData<List<Any>>()

    fun loadFavoriteNodes() {
        request {
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
            nodesLiveData.value = result
        }
    }

}