package com.imhanjie.v2ex.view.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.imhanjie.v2ex.AppSession
import com.imhanjie.v2ex.BaseLazyFragment
import com.imhanjie.v2ex.api.model.TinyNode
import com.imhanjie.v2ex.databinding.FragmentTabNodeBinding
import com.imhanjie.v2ex.view.NodeActivity
import com.imhanjie.v2ex.view.adapter.NodeItemAdapter
import com.imhanjie.v2ex.view.adapter.NodeTypeAdapter
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.NodeTabViewModel
import com.imhanjie.widget.LoadingWrapLayout

class NodeTabFragment : BaseLazyFragment<FragmentTabNodeBinding>() {

    private val vm: NodeTabViewModel by viewModels()

    private val items = arrayListOf<Any>()

    override fun initViewModels(): List<BaseViewModel> {
        return listOf(vm)
    }

    override fun initViews() {
        AppSession.getLoginState().observe(viewLifecycleOwner) {
            if (it && !isFirstResume) {
                vm.loadFavoriteNodes()
            }
        }

        vb.loadingLayout.update(LoadingWrapLayout.Status.LOADING)
        vb.swipeRefreshLayout.setOnRefreshListener { vm.loadFavoriteNodes() }

        val adapter = MultiTypeAdapter(items)
        adapter.apply {
            register(String::class.java, NodeTypeAdapter())
            register(TinyNode::class.java, NodeItemAdapter().apply {
                onItemClickListener = { _, item, _ ->
                    NodeActivity.start(this@NodeTabFragment, item.title, item.name)
                }
            })
        }

        vb.rv.layoutManager = GridLayoutManager(requireContext(), 3).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (items[position] is String) spanCount else 1
                }
            }
        }
        vb.rv.adapter = adapter

        vm.loadState.observe(viewLifecycleOwner) {
            vb.swipeRefreshLayout.isRefreshing = false
        }

        vm.nodes.observe(viewLifecycleOwner) {
            vb.loadingLayout.update(LoadingWrapLayout.Status.DONE)
            items.clear()
            items.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onFirstResume() {
        vm.loadFavoriteNodes()
    }

}