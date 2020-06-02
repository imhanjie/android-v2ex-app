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
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.common.MissingArgumentException
import com.imhanjie.v2ex.common.ViewModelProvider
import com.imhanjie.v2ex.databinding.ActivityMemberBinding
import com.imhanjie.v2ex.view.fragment.MemberRepliesFragment
import com.imhanjie.v2ex.view.fragment.MemberTopicsFragment
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.MemberViewModel

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
    }

    private fun initViews() {
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

}