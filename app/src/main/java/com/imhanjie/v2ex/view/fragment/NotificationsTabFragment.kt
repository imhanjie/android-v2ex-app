package com.imhanjie.v2ex.view.fragment

import androidx.lifecycle.ViewModelProvider
import com.imhanjie.v2ex.AppSession
import com.imhanjie.v2ex.BaseLazyFragment
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.FragmentTabNotificationsBinding
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.NotificationsViewModel

class NotificationsTabFragment : BaseLazyFragment<FragmentTabNotificationsBinding>() {

    private lateinit var vm: NotificationsViewModel

    override fun initViewModels(): List<BaseViewModel> {
        vm = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        return listOf(vm)
    }

    override fun initViews() {
        AppSession.getLoginState().observe(viewLifecycleOwner) {
            if (it && !isFirstResume) {
                vm.loadDataList(loadMore = false)
            }
        }
        val preFragment = childFragmentManager.findFragmentById(R.id.list_container)
        if (preFragment == null) {
            childFragmentManager
                .beginTransaction()
                .replace(R.id.list_container, NotificationsPageFragment())
                .commit()
        }
    }

    override fun onFirstResume() {
        vm.loadDataList(loadMore = false)
    }

}