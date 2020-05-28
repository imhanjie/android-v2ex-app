package com.imhanjie.v2ex.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.imhanjie.support.ext.dpi
import com.imhanjie.support.ext.toActivity
import com.imhanjie.support.ext.toast
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.api.model.TopicItem
import com.imhanjie.v2ex.databinding.ActivityNodeBinding
import com.imhanjie.v2ex.view.adapter.TopicAdapter
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.NodeViewModel
import com.imhanjie.widget.LineDividerItemDecoration
import com.imhanjie.widget.LoadingWrapLayout
import com.imhanjie.widget.recyclerview.loadmore.LoadMoreDelegate

class NodeActivity : BaseActivity<ActivityNodeBinding>() {

    private lateinit var vm: NodeViewModel

    @Suppress("UNCHECKED_CAST")
    override fun initViewModels(): List<BaseViewModel> {
        val nodeName: String = intent.getStringExtra("name")
            ?: throw IllegalArgumentException("缺少 name 参数")
        vm = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NodeViewModel(nodeName, application) as T
            }
        }).get(NodeViewModel::class.java)
        return listOf(vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title: String = intent.getStringExtra("title")
            ?: throw IllegalArgumentException("缺少 title 参数")

        vb.loadingLayout.update(LoadingWrapLayout.Status.LOADING)
        vb.topBar.setTitleText(title)
        vb.topBar.setOnRightClickListener(View.OnClickListener {
            vm.doFavoriteNode()
        })
        vb.topBar.setOnClickListener { vb.topicRv.smoothScrollToPosition(0) }
        vb.swipeRefreshLayout.setOnRefreshListener {
            vm.loadNodeTopics(append = false)
        }
        vm.isFavorite.observe(this) {
            val (isFavorite, isManual) = it
            if (isManual) {
                toast(if (isFavorite) R.string.tips_favorite_success else R.string.tips_un_favorite_success)
            }
            vb.topBar.setRightIcon(if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_un_favorite)
        }

        vb.topicRv.addItemDecoration(LineDividerItemDecoration(this, height = 4.dpi))
        val delegate = LoadMoreDelegate(vb.topicRv) {
            vm.loadNodeTopics(append = true)
        }

        val topicAdapter = TopicAdapter().apply {
            onItemClickListener = { _, item, _ ->
                this@NodeActivity.toActivity<TopicActivity>(bundleOf("topicId" to item.id))
            }
        }
        delegate.adapter.apply {
            register(TopicItem::class.java, topicAdapter)
        }
        vm.node.observe(this) {
            vb.loadingLayout.update(LoadingWrapLayout.Status.DONE)
            vb.swipeRefreshLayout.isRefreshing = false

            val (node, append, hasMore) = it
            delegate.apply {
                val isFirst = items.isEmpty()
                if (isFirst || !append) {
                    items.clear()
                    items.addAll(node.topics)
                    adapter.notifyDataSetChanged()
                } else {
                    adapter.notifyItemChanged(items.itemSize - 1)   // fix 最后一项 divider 不刷新的问题
                    val originSize = items.itemSize
                    items.addAll(node.topics)
                    adapter.notifyItemRangeInserted(originSize, node.topics.size)
                }
                notifyLoadSuccess(hasMore)
            }
        }
    }

}