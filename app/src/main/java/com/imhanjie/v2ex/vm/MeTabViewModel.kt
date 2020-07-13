package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import com.imhanjie.v2ex.AppSession
import com.imhanjie.v2ex.common.NonStickyLiveData

class MeTabViewModel(application: Application) : BaseViewModel(application) {

    private val _loadState = NonStickyLiveData<Boolean>()

    val loadState: LiveData<Boolean>
        get() = _loadState

    fun loadMyUserInfo() {
        request(onError = {
            _loadState.value = false
        }) {
            val result = repo.loadMyUserInfo()
            AppSession.setOrUpdateUserInfo(result)
            _loadState.value = true
        }
    }

}