package com.imhanjie.v2ex.common

import androidx.lifecycle.ViewModel

class GlobalViewModel : ViewModel() {

    val ignoreTopic = NonStickyLiveData<Long>()

}