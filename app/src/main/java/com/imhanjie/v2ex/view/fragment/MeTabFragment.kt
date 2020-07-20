package com.imhanjie.v2ex.view.fragment

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.imhanjie.support.ext.toActivity
import com.imhanjie.support.ext.toast
import com.imhanjie.v2ex.AppSession
import com.imhanjie.v2ex.BaseLazyFragment
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.FragmentTabMeBinding
import com.imhanjie.v2ex.view.FavoriteTopicsActivity
import com.imhanjie.v2ex.view.MemberActivity
import com.imhanjie.v2ex.view.widget.AboutDialog
import com.imhanjie.v2ex.vm.MeTabViewModel

class MeTabFragment : BaseLazyFragment<FragmentTabMeBinding>() {

    private val vm: MeTabViewModel by viewModels()

    override fun initViewModels() = listOf(vm)

    override fun initViews() {
        with(vb) {
            swipeRefreshLayout.setOnRefreshListener {
                vm.loadMyUserInfo()
            }
            viewFavoriteTopics.setOnClickListener { this@MeTabFragment.toActivity<FavoriteTopicsActivity>() }
            viewUserInfo.setOnClickListener { MemberActivity.start(this@MeTabFragment, tvUserName.text.toString()) }
            viewSettings.setOnClickListener { toast(R.string.coming_soon) }
            viewAbout.setOnClickListener { AboutDialog(requireContext()).show() }
        }

        vm.loadState.observe(viewLifecycleOwner) {
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