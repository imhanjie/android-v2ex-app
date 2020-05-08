package com.imhanjie.v2ex.view

import com.imhanjie.support.e
import com.imhanjie.v2ex.BaseLazyFragment
import com.imhanjie.v2ex.databinding.FragmentTabNotificationBinding
import com.imhanjie.v2ex.vm.BaseViewModel

class NotificationTabFragment : BaseLazyFragment<FragmentTabNotificationBinding>() {

    override fun getViewModels(): List<BaseViewModel> {
        return emptyList()
    }

    override fun initViews() {

    }

    override fun onLazyLoad() {
        e("onLazyLoad: NotificationTabFragment()")
    }

    override fun onResumeAfterLazyLoad() {
        super.onResumeAfterLazyLoad()
        e("onResumeAfterLazyLoad: NotificationTabFragment()")
    }

}