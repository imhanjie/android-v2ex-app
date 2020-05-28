package com.imhanjie.v2ex.view

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import com.imhanjie.support.ext.dpi
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.api.model.TopicItem
import com.imhanjie.v2ex.databinding.ActivityFavoriteTopicsBinding
import com.imhanjie.v2ex.view.adapter.TopicAdapter
import com.imhanjie.v2ex.view.adapter.diff.TopicDiffCallback
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.FavoriteTopicsViewModel
import com.imhanjie.widget.LineDividerItemDecoration
import com.imhanjie.widget.LoadingWrapLayout
import com.imhanjie.widget.recyclerview.loadmore.LoadMoreDelegate

class FavoriteTopicsActivity : BaseActivity<ActivityFavoriteTopicsBinding>() {

    private lateinit var vm: FavoriteTopicsViewModel

    @Suppress("UNCHECKED_CAST")
    override fun initViewModels(): List<BaseViewModel> {
        vm = ViewModelProvider(this).get(FavoriteTopicsViewModel::class.java)
        return listOf(vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb.loadingLayout.update(LoadingWrapLayout.Status.LOADING)
        vb.topBar.setOnClickListener { vb.topicRv.smoothScrollToPosition(0) }
        vb.swipeRefreshLayout.setOnRefreshListener {
            vm.loadFavoriteTopics(append = false)
        }
        vb.topicRv.addItemDecoration(LineDividerItemDecoration(this, height = 4.dpi))
        val delegate = LoadMoreDelegate(vb.topicRv) {
            vm.loadFavoriteTopics(append = true)
        }

        val topicAdapter = TopicAdapter().apply {
            onItemClickListener = { _, item, _ ->
                this@FavoriteTopicsActivity.toActivity<TopicActivity>(
                    bundleOf(
                        "topicId" to item.id,
                        "from_favorite_topics" to true
                    )
                )
            }
        }
        delegate.adapter.apply {
            register(TopicItem::class.java, topicAdapter)
        }
        vm.topics.observe(this) {
            vb.loadingLayout.update(LoadingWrapLayout.Status.DONE)
            vb.swipeRefreshLayout.isRefreshing = false

            val (topics, append, hasMore, diff) = it
            delegate.apply {
                if (diff) {
                    val diffResult = DiffUtil.calculateDiff(TopicDiffCallback(items, topics))
                    items.clear()
                    items.addAll(topics)
                    diffResult.dispatchUpdatesTo(adapter)
                } else {
                    val isFirst = items.isEmpty()
                    if (isFirst || !append) {
                        items.clear()
                        items.addAll(topics)
                        adapter.notifyDataSetChanged()
                    } else {
                        adapter.notifyItemChanged(items.itemSize - 1)   // fix 最后一项 divider 不刷新的问题
                        val originSize = items.itemSize
                        items.addAll(topics)
                        adapter.notifyItemRangeInserted(originSize, topics.size)
                    }
                    notifyLoadSuccess(hasMore)
                }
            }
        }

        globalViewModel.unFavoriteTopic.observe(this) { topicId ->
            vm.removeItem(topicId)
        }
    }

}