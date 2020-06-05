package com.imhanjie.v2ex.view.fragment

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.AppSession
import com.imhanjie.v2ex.BaseLazyFragment
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.FragmentTabMeBinding
import com.imhanjie.v2ex.view.FavoriteTopicsActivity
import com.imhanjie.v2ex.view.MemberActivity
import com.imhanjie.v2ex.vm.MeTabViewModel

class MeTabFragment : BaseLazyFragment<FragmentTabMeBinding>() {

    private lateinit var vm: MeTabViewModel

    override fun initViewModels() = listOf(vm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(this).get(MeTabViewModel::class.java)
    }

    override fun initViews() {
        vb.swipeRefreshLayout.setOnRefreshListener {
            vm.loadMyUserInfo()
        }

        AppSession.getUserInfo().observe(viewLifecycleOwner) {
            vb.swipeRefreshLayout.isRefreshing = false
            vb.tvUserName.text = it.userName
            Glide.with(this)
                .load(it.userAvatar)
                .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.default_avatar))
                .transform(CircleCrop())
                .into(vb.ivUserAvatar)
            vb.tvMoneyGold.text = it.moneyGold.toString()
            vb.tvMoneySilver.text = it.moneySilver.toString()
            vb.tvMoneyBronze.text = it.moneyBronze.toString()
        }
        vb.viewUserInfo.setOnClickListener { MemberActivity.start(this, vb.tvUserName.text.toString()) }
        vb.viewFavoriteTopics.setOnClickListener { toActivity<FavoriteTopicsActivity>() }
    }

    override fun onFirstResume() {
        vm.loadMyUserInfo()
    }

}