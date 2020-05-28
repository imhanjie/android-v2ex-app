package com.imhanjie.v2ex.vm

import android.app.Application
import com.imhanjie.support.e
import com.imhanjie.v2ex.api.model.Member
import com.imhanjie.v2ex.repository.provideAppRepository

class MemberViewModel(val userName: String, application: Application) : BaseViewModel(application) {

    private lateinit var member: Member

    init {
        loadMember()
    }

    private fun loadMember() {
        request {
            member = provideAppRepository().loadMember(userName)
            e("loadMember: $member")
        }
    }

    fun followMember() {
        request {
            val newMember = provideAppRepository().followMember(member.id, member.name, member.once)
            e("newMember: $newMember")
        }
    }

    fun unFollowMember() {
        request {
            val newMember = provideAppRepository().unFollowMember(member.id, member.name, member.once)
            e("newMember: $newMember")
        }
    }

    fun blockMember() {
        request {
            val newMember = provideAppRepository().blockMember(member.id, member.name, member.blockParamT)
            e("newMember: $newMember")
        }
    }

    fun unBlockMember() {
        request {
            val newMember = provideAppRepository().unBlockMember(member.id, member.name, member.blockParamT)
            e("newMember: $newMember")
        }
    }

}