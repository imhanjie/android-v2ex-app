package com.imhanjie.v2ex.common

import androidx.lifecycle.ViewModel
import com.imhanjie.v2ex.common.extension.NonStickyLiveData

class GlobalViewModel : ViewModel() {

    val ignoreTopic = NonStickyLiveData<Long>()
    val unFavoriteTopic = NonStickyLiveData<Long>()
    val appendTopic = NonStickyLiveData<Any>()

}