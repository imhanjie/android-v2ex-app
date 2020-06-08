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
        with(vb) {
            swipeRefreshLayout.setOnRefreshListener {
                vm.loadMyUserInfo()
            }
            viewFavoriteTopics.setOnClickListener { this@MeTabFragment.toActivity<FavoriteTopicsActivity>() }
            viewUserInfo.setOnClickListener { MemberActivity.start(this@MeTabFragment, tvUserName.text.toString()) }
        }

        vm.loadState.observe(this) {
            vb.swipeRefreshLayout.isRefreshing = false
        }

        AppSession.getUserInfo().observe(viewLifecycleOwner) { info ->
            with(vb) {
                tvUserName.text = info.userName
                Glide.with(this@MeTabFragment)
                    .load(info.userAvatar)
                    .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.default_avatar))
                    .transform(CircleCrop())
                    .into(ivUserAvatar)
                tvMoneyGold.text = info.moneyGold.toString()
                tvMoneySilver.text = info.moneySilver.toString()
                tvMoneyBronze.text = info.moneyBronze.toString()
            }
        }
    }

    override fun onFirstResume() {
        vm.loadMyUserInfo()
    }

}