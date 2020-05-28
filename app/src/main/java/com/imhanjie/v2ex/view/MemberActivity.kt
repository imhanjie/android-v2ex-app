package com.imhanjie.v2ex.view

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.databinding.ActivityMemberBinding
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.MemberViewModel

class MemberActivity : BaseActivity<ActivityMemberBinding>() {

    private lateinit var vm: MemberViewModel

    @Suppress("UNCHECKED_CAST")
    override fun initViewModels(): List<BaseViewModel> {
        val userName: String = intent.getStringExtra("userName")
            ?: throw IllegalArgumentException("缺少 userName 参数")
        vm = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MemberViewModel(userName, application) as T
            }
        }).get(MemberViewModel::class.java)
        return listOf(vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb.btnFollow.setOnClickListener { vm.followMember() }
        vb.btnUnFollow.setOnClickListener { vm.unFollowMember() }
        vb.btnBlock.setOnClickListener { vm.blockMember() }
        vb.btnUnBlock.setOnClickListener { vm.unBlockMember() }
    }

}