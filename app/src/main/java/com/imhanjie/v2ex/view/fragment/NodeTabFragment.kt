package com.imhanjie.v2ex.view.fragment

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.imhanjie.support.ext.toActivity
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

    private lateinit var vm: NodeTabViewModel

    private val items = arrayListOf<Any>()

    override fun initViewModels(): List<BaseViewModel> {
        vm = ViewModelProvider(this).get(NodeTabViewModel::class.java)
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
                    this@NodeTabFragment.toActivity<NodeActivity>(
                        bundleOf(
                            "title" to item.title,
                            "name" to item.name
                        )
                    )
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

        vm.nodes.observe(this) {
            vb.loadingLayout.update(LoadingWrapLayout.Status.DONE)
            vb.swipeRefreshLayout.isRefreshing = false
            items.clear()
            items.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onFirstResume() {
        vm.loadFavoriteNodes()
    }

}