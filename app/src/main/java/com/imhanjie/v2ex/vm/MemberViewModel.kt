package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.imhanjie.v2ex.api.model.Member
import com.imhanjie.v2ex.common.ExtraKeys
import com.imhanjie.v2ex.common.exception.MissingArgumentException
import com.imhanjie.v2ex.common.extension.NonStickyLiveData

class MemberViewModel(application: Application, savedStateHandle: SavedStateHandle) : BaseViewModel(application) {

    val userName: String = savedStateHandle[ExtraKeys.USER_NAME]
        ?: throw MissingArgumentException(ExtraKeys.USER_NAME)

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
            _member.value = repo.loadMember(userName)
        }
    }

    fun followMember() {
        request(withLoading = true) {
            _member.value = _member.value?.let {
                repo.followMember(it.id, it.name, it.once)
            }
            _followState.value = true
        }
    }

    fun unFollowMember() {
        request(withLoading = true) {
            _member.value = _member.value?.let {
                repo.unFollowMember(it.id, it.name, it.once)
            }
            _followState.value = false
        }
    }

    fun blockMember() {
        request(withLoading = true) {
            _member.value = _member.value?.let {
                repo.blockMember(it.id, it.name, it.blockParamT)
            }
            _blockState.value = true
        }
    }

    fun unBlockMember() {
        request(withLoading = true) {
            _member.value = _member.value?.let {
                repo.unBlockMember(it.id, it.name, it.blockParamT)
            }
            _blockState.value = false
        }
    }

}