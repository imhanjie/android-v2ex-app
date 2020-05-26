package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.api.model.MyUserInfo
import com.imhanjie.v2ex.repository.provideAppRepository

class MeTabViewModel(application: Application) : BaseViewModel(application) {

    private val _userInfo = MutableLiveData<MyUserInfo>()

    val userInfo: LiveData<MyUserInfo>
        get() = _userInfo

    fun loadMyUserInfo() {
        request {
            _userInfo.value = provideAppRepository().loadMyUserInfo()
        }
    }

}