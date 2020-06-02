package com.imhanjie.v2ex.view

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.tabs.TabLayoutMediator
import com.imhanjie.support.ext.toast
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.common.MissingArgumentException
import com.imhanjie.v2ex.common.ViewModelProvider
import com.imhanjie.v2ex.databinding.ActivityMemberBinding
import com.imhanjie.v2ex.view.fragment.MemberRepliesFragment
import com.imhanjie.v2ex.view.fragment.MemberTopicsFragment
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.MemberViewModel
import com.imhanjie.widget.dialog.PureListMenuDialog

class MemberActivity : BaseActivity<ActivityMemberBinding>() {

    private lateinit var vm: MemberViewModel
    private lateinit var userName: String

    @Suppress("UNCHECKED_CAST")
    override fun initViewModels(): List<BaseViewModel> {
        userName = intent.getStringExtra("userName")
            ?: throw MissingArgumentException("userName")
        vm = ViewModelProvider(this) { MemberViewModel(userName, application) }
        return listOf(vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        vm.member.observe(this) { member ->
            if (!member.isMe) {
                vb.topBar.setRightVisibility(View.VISIBLE)
            }
            Glide.with(this)
                .load(member.avatar)
                .placeholder(ContextCompat.getDrawable(this, R.drawable.default_avatar))
                .transform(CircleCrop())
                .into(vb.ivAvatar)
        }
        vm.followState.observe(this) { follow ->
            if (follow) {
                toast(R.string.tips_follow_member_success)
            } else {
                toast(R.string.tips_un_follow_member_success)
            }
        }
        vm.blockState.observe(this) { block ->
            if (block) {
                toast(R.string.tips_block_member_success)
            } else {
                toast(R.string.tips_un_block_member_success)
            }
        }
    }

    private fun initViews() {
        vb.topBar.setOnRightClickListener(View.OnClickListener {
            showMemberMenuDialog()
        })
        initViewPager()
        vb.tvUserName.text = userName
        Glide.with(this)
            .load(ContextCompat.getDrawable(this, R.drawable.default_avatar))
            .transform(CircleCrop())
            .into(vb.ivAvatar)
    }

    private val fragments = arrayListOf<Fragment>()

    private fun initViewPager() {
        with(fragments) {
            add(MemberRepliesFragment.newInstance(userName))
            add(MemberTopicsFragment.newInstance(userName))
        }
        (vb.viewPager.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        vb.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragments.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }
        }
        vb.viewPager.offscreenPageLimit = fragments.size
        TabLayoutMediator(vb.tabLayout, vb.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "TA 的回复"
                1 -> tab.text = "TA 的主题"
            }
        }.attach()
    }

    private fun showMemberMenuDialog() {
        PureListMenuDialog(this).apply {
            withCancelable(true)
            val member = vm.member.value!!
            if (!member.isFollowing) {
                withMenuItem(PureListMenuDialog.Item(getString(R.string.menu_follow_member), onClickListener = { vm.followMember() }))
            } else {
                withMenuItem(PureListMenuDialog.Item(getString(R.string.menu_un_follow_member), onClickListener = { vm.unFollowMember() }))
            }
            if (!member.isBlock) {
                withMenuItem(PureListMenuDialog.Item(getString(R.string.menu_block_member), onClickListener = { vm.blockMember() }))
            } else {
                withMenuItem(PureListMenuDialog.Item(getString(R.string.menu_un_block_member), onClickListener = { vm.unBlockMember() }))
            }
            show()
        }
    }

}