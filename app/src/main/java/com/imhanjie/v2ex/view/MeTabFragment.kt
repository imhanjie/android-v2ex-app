package com.imhanjie.v2ex.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.imhanjie.support.e
import com.imhanjie.v2ex.BaseFragment
import com.imhanjie.v2ex.databinding.FragmentTabMeBinding
import com.imhanjie.v2ex.vm.MeTabViewModel

class MeTabFragment : BaseFragment<FragmentTabMeBinding>() {

    private lateinit var vm: MeTabViewModel

    override fun getViewModels() = listOf(vm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(this).get(MeTabViewModel::class.java)
    }

    override fun initViews() {
        vm.myUserInfo.observe(this) {
            e(it.toString())
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
    }

    override fun onResume() {
        super.onResume()
        e("MeTabFragment: onResume()")
    }

    override fun onPause() {
        super.onPause()
        e("MeTabFragment: onPause()")
    }

}