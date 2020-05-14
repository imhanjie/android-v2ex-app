package com.imhanjie.v2ex.view.fragment

import androidx.lifecycle.ViewModelProvider
import com.imhanjie.v2ex.BaseLazyFragemnt
import com.imhanjie.v2ex.databinding.FragmentTabNodeBinding
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.NodeTabViewModel

class NodeTabFragment : BaseLazyFragemnt<FragmentTabNodeBinding>() {

    private lateinit var vm: NodeTabViewModel

    override fun getViewModels(): List<BaseViewModel> {
        vm = ViewModelProvider(this).get(NodeTabViewModel::class.java)
        return listOf(vm)
    }

    override fun initViews() {

    }

    override fun onFirstResume() {
        vm.loadFavoriteNodes()
    }

}