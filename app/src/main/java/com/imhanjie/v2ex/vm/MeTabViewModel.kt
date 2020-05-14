package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.parser.model.MyUserInfo
import com.imhanjie.v2ex.repository.provideAppRepository

class MeTabViewModel(application: Application) : BaseViewModel(application) {

    val userInfoLiveData = MutableLiveData<MyUserInfo>()

    fun loadMyUserInfo() {
        request {
            userInfoLiveData.value = provideAppRepository().loadMyUserInfo()
//            provideAppRepository().loadAllNode()
        }
    }

}