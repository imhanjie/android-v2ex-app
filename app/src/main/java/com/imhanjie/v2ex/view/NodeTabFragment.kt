package com.imhanjie.v2ex.view

import com.imhanjie.support.e
import com.imhanjie.v2ex.BaseFragment
import com.imhanjie.v2ex.databinding.FragmentTabNodeBinding
import com.imhanjie.v2ex.vm.BaseViewModel

class NodeTabFragment : BaseFragment<FragmentTabNodeBinding>() {

    override fun getViewModels(): List<BaseViewModel> {
        return emptyList()
    }

    override fun initViews() {

    }

    override fun onResume() {
        super.onResume()
        e("NodeTabFragment: onResume()")
    }

    override fun onPause() {
        super.onPause()
        e("NodeTabFragment: onPause()")
    }

}