package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.api.model.Member
import com.imhanjie.v2ex.common.NonStickyLiveData
import com.imhanjie.v2ex.repository.provideAppRepository

class MemberViewModel(private val userName: String, application: Application) : BaseViewModel(application) {

    private val _member = MutableLiveData<Member>()

    val member: LiveData<Member>
        get() = _member

    private val _followState = NonStickyLiveData<Boolean>()

    val followState: LiveData<Boolean>
        get() = _followState

    private val _blockState = NonStickyLiveData<Boolean>()

    val blockState: LiveData<Boolean>
        get() = _blockState

    init {
        loadMember()
    }

    private fun loadMember() {
        request {
            _member.value = provideAppRepository().loadMember(userName)
        }
    }

    fun followMember() {
        request(withLoading = true) {
            _member.value = _member.value?.let {
                provideAppRepository().followMember(it.id, it.name, it.once)
            }
            _followState.value = true
        }
    }

    fun unFollowMember() {
        request(withLoading = true) {
            _member.value = _member.value?.let {
                provideAppRepository().unFollowMember(it.id, it.name, it.once)
            }
            _followState.value = false
        }
    }

    fun blockMember() {
        request(withLoading = true) {
            _member.value = _member.value?.let {
                provideAppRepository().blockMember(it.id, it.name, it.blockParamT)
            }
            _blockState.value = true
        }
    }

    fun unBlockMember() {
        request(withLoading = true) {
            _member.value = _member.value?.let {
                provideAppRepository().unBlockMember(it.id, it.name, it.blockParamT)
            }
            _blockState.value = false
        }
    }

}