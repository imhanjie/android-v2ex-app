package com.imhanjie.v2ex.view.fragment

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import com.drakeet.multitype.MultiTypeAdapter
import com.imhanjie.v2ex.api.model.TopicItem
import com.imhanjie.v2ex.view.BasePageFragment
import com.imhanjie.v2ex.view.TopicActivity
import com.imhanjie.v2ex.view.adapter.TopicAdapter
import com.imhanjie.v2ex.view.adapter.diff.TopicDiffCallback
import com.imhanjie.v2ex.vm.NodeViewModel

class NodePageFragment : BasePageFragment<NodeViewModel>() {

    override fun getViewModel(): NodeViewModel {
        /*
         * 这里从依附的 activity 的 ViewModelStoreOwner 中取出 NodeViewModel
         */
        return ViewModelProvider(requireActivity()).get(NodeViewModel::class.java)
    }

    override fun getDiffCallback(oldItems: List<Any>, newItems: List<Any>): DiffUtil.Callback {
        return TopicDiffCallback(oldItems, newItems)
    }

    override fun registerAdapter(adapter: MultiTypeAdapter) {
        val topicAdapter = TopicAdapter().apply {
            onItemClickListener = { _, item, _ ->
                TopicActivity.start(this@NodePageFragment, item.id)
            }
        }
        adapter.register(TopicItem::class.java, topicAdapter)
    }

}