package com.imhanjie.v2ex.vm

import android.app.Application
import com.imhanjie.v2ex.repository.provideAppRepository

class NodeTabViewModel(application: Application) : BaseViewModel(application) {

    fun loadFavoriteNodes() {
        request {
            val nodes = provideAppRepository().loadFavoriteNodes()
            for (node in nodes) {

            }
        }
    }

}