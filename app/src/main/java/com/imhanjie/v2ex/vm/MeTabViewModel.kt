package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.parser.model.MyUserInfo
import com.imhanjie.v2ex.repository.provideAppRepository

class MeTabViewModel(application: Application) : BaseViewModel(application) {

    val myUserInfo = MutableLiveData<MyUserInfo>()

    init {
        loadMyUserInfo()
    }

    public fun loadMyUserInfo() {
        request {
            myUserInfo.value = provideAppRepository().loadMyUserInfo()
        }
    }

}