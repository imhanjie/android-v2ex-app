package com.imhanjie.v2ex.view.fragment

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import com.drakeet.multitype.MultiTypeAdapter
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.api.model.TopicItem
import com.imhanjie.v2ex.view.BasePageFragment
import com.imhanjie.v2ex.view.TopicActivity
import com.imhanjie.v2ex.view.adapter.TopicAdapter
import com.imhanjie.v2ex.view.adapter.diff.TopicDiffCallback
import com.imhanjie.v2ex.vm.NodeViewModel

class NodePageFragment : BasePageFragment<NodeViewModel>() {

    override fun getViewModel(): NodeViewModel {
        /*
         * 这里从依附的 activity 的 ViewModelStoreOwner 中取出 NodeViewModel，
         * 不会走 ViewModelFactory 的 create 方法了，所以不用传构造器参数给 NodeViewModel 了
         */
        return ViewModelProvider(requireActivity()).get(NodeViewModel::class.java)
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

}