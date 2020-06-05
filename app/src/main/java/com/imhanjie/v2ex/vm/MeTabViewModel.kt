package com.imhanjie.v2ex.vm

import android.app.Application
import com.imhanjie.v2ex.AppSession
import com.imhanjie.v2ex.repository.provideAppRepository

class MeTabViewModel(application: Application) : BaseViewModel(application) {

    fun loadMyUserInfo() {
        request {
            val result = provideAppRepository().loadMyUserInfo()
            AppSession.setOrUpdateUserInfo(result)
        }
    }

}