package com.imhanjie.v2ex.view

import com.imhanjie.support.e
import com.imhanjie.v2ex.BaseFragment
import com.imhanjie.v2ex.databinding.FragmentTabNotificationBinding
import com.imhanjie.v2ex.vm.BaseViewModel

class NotificationTabFragment : BaseFragment<FragmentTabNotificationBinding>() {

    override fun getViewModels(): List<BaseViewModel> {
        return emptyList()
    }

    override fun initViews() {
    }

    override fun onResume() {
        super.onResume()
        e("NotificationTabFragment: onResume()")
    }

    override fun onPause() {
        super.onPause()
        e("NotificationTabFragment: onPause()")
    }

}