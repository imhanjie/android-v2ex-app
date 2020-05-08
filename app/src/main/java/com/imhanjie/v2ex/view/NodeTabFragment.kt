package com.imhanjie.v2ex.view

import com.imhanjie.support.e
import com.imhanjie.v2ex.BaseLazyFragment
import com.imhanjie.v2ex.databinding.FragmentTabNodeBinding
import com.imhanjie.v2ex.vm.BaseViewModel

class NodeTabFragment : BaseLazyFragment<FragmentTabNodeBinding>() {

    override fun getViewModels(): List<BaseViewModel> {
        return emptyList()
    }

    override fun initViews() {

    }

    override fun onLazyLoad() {
        e("onLazyLoad: NodeTabFragment()")
    }

    override fun onResumeAfterLazyLoad() {
        super.onResumeAfterLazyLoad()
        e("onResumeAfterLazyLoad: NodeTabFragment()")
    }

}