package com.imhanjie.v2ex.view.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.imhanjie.support.e
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.BaseFragment
import com.imhanjie.v2ex.common.valueIsNull
import com.imhanjie.v2ex.databinding.FragmentTabMeBinding
import com.imhanjie.v2ex.view.FavoriteTopicsActivity
import com.imhanjie.v2ex.vm.MeTabViewModel

class MeTabFragment : BaseFragment<FragmentTabMeBinding>() {

    private lateinit var vm: MeTabViewModel

    override fun getViewModels() = listOf(vm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(this).get(MeTabViewModel::class.java)
    }

    override fun initViews() {
        vm.userInfo.observe(viewLifecycleOwner) {
            e(it.toString())
        }
        vb.root.setOnClickListener {
            toActivity<FavoriteTopicsActivity>()
        }
    }

    override fun onResume() {
        super.onResume()
        if (vm.userInfo.valueIsNull()) {
            vm.loadMyUserInfo()
        }
    }

}