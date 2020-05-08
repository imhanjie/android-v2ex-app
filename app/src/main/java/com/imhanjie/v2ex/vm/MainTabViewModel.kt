package com.imhanjie.v2ex.vm

import android.app.Application
import com.imhanjie.v2ex.common.TopicTab

class MainTabViewModel(application: Application) : BaseViewModel(application) {

    val tabs = TopicTab.values()

}