package com.imhanjie.v2ex.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.imhanjie.support.e
import com.imhanjie.v2ex.BaseLazyFragment
import com.imhanjie.v2ex.databinding.FragmentTabMeBinding
import com.imhanjie.v2ex.vm.MeTabViewModel

class MeTabFragment : BaseLazyFragment<FragmentTabMeBinding>() {

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

    override fun onLazyLoad() {
        e("onLazyLoad: MeTabFragment()")
    }

    override fun onResumeAfterLazyLoad() {
        super.onResumeAfterLazyLoad()
        e("onResumeAfterLazyLoad: MeTabFragment()")
    }

}