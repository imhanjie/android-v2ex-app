package com.imhanjie.v2ex.view.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.imhanjie.support.e
import com.imhanjie.v2ex.AppSession
import com.imhanjie.v2ex.BaseLazyFragment
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.FragmentTabNotificationsBinding
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.NotificationsViewModel

class NotificationsTabFragment : BaseLazyFragment<FragmentTabNotificationsBinding>() {

    private lateinit var vm: NotificationsViewModel

    override fun getViewModels(): List<BaseViewModel> {
        vm = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        return listOf(vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        e("NotificationsTabFragment oncreate: $this")
    }

    override fun initViews() {
        AppSession.getLoginStateLiveData().observe(this) {
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