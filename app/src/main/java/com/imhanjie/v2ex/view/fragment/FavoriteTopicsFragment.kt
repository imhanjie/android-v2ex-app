package com.imhanjie.v2ex.view.fragment

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import com.drakeet.multitype.MultiTypeAdapter
import com.imhanjie.v2ex.api.model.TopicItem
import com.imhanjie.v2ex.view.BasePageFragment
import com.imhanjie.v2ex.view.TopicActivity
import com.imhanjie.v2ex.view.adapter.TopicAdapter
import com.imhanjie.v2ex.view.adapter.diff.TopicDiffCallback
import com.imhanjie.v2ex.vm.FavoriteTopicsViewModel

class FavoriteTopicsFragment : BasePageFragment<FavoriteTopicsViewModel>() {

    override fun initViews() {
        super.initViews()
        globalViewModel.unFavoriteTopic.observe(viewLifecycleOwner) { id -> vm.removeTopic(id) }
    }

    override fun getViewModel(): FavoriteTopicsViewModel {
        return ViewModelProvider(requireActivity()).get(FavoriteTopicsViewModel::class.java)
    }

    override fun getDiffCallback(oldItems: List<Any>, newItems: List<Any>): DiffUtil.Callback {
        return TopicDiffCallback(oldItems, newItems)
    }

    override fun registerAdapter(adapter: MultiTypeAdapter) {
        val topicAdapter = TopicAdapter().apply {
            onItemClickListener = { _, item, _ ->
                TopicActivity.start(this@FavoriteTopicsFragment, item.id, true)
            }
        }
        adapter.register(TopicItem::class.java, topicAdapter)
    }

}