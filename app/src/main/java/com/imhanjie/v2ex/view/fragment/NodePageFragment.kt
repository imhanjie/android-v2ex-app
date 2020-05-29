package com.imhanjie.v2ex.view.fragment

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import com.drakeet.multitype.MultiTypeAdapter
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.api.model.TopicItem
import com.imhanjie.v2ex.common.MissingArgumentException
import com.imhanjie.v2ex.view.BasePageFragment
import com.imhanjie.v2ex.view.TopicActivity
import com.imhanjie.v2ex.view.adapter.TopicAdapter
import com.imhanjie.v2ex.view.adapter.diff.TopicDiffCallback
import com.imhanjie.v2ex.vm.NodePageViewModel

class NodePageFragment : BasePageFragment<NodePageViewModel>() {

    @Suppress("UNCHECKED_CAST")
    override fun getViewModel(): NodePageViewModel {
        val nodeName = arguments?.getString("nodeName")
            ?: throw MissingArgumentException("nodeName")
        vm = ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NodePageViewModel(nodeName, requireActivity().application) as T
            }
        }).get(NodePageViewModel::class.java)
        return vm
    }

    override fun getDiffCallback(oldItems: List<Any>, newItems: List<Any>): DiffUtil.Callback {
        return TopicDiffCallback(oldItems, newItems)
    }

    override fun registerAdapter(adapter: MultiTypeAdapter) {
        val topicAdapter = TopicAdapter().apply {
            onItemClickListener = { _, item, _ ->
                this@NodePageFragment.toActivity<TopicActivity>(bundleOf("topicId" to item.id))
            }
        }
        adapter.register(TopicItem::class.java, topicAdapter)
    }

    companion object {
        fun newInstance(nodeName: String): NodePageFragment {
            val fragment = NodePageFragment()
            fragment.arguments = Bundle().apply {
                putSerializable("nodeName", nodeName)
            }
            return fragment
        }
    }

}