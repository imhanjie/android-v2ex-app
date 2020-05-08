package com.imhanjie.v2ex.view

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imhanjie.support.ext.dp
import com.imhanjie.v2ex.App
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.databinding.ActivityTopicBinding
import com.imhanjie.v2ex.model.ReplyHeaderType
import com.imhanjie.v2ex.parser.model.Reply
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.TopicViewModel
import com.imhanjie.widget.LineDividerItemDecoration
import com.imhanjie.widget.LoadingWrapLayout
import com.imhanjie.widget.recyclerview.loadmore.LoadMoreDelegate

class TopicActivity : BaseActivity<ActivityTopicBinding>() {

    private lateinit var vm: TopicViewModel

    @Suppress("UNCHECKED_CAST")
    override fun initViewModels(): List<BaseViewModel> {
        val topicId: Long = intent.getLongExtra("topicId", -1)
        if (topicId < 0) {
            throw IllegalArgumentException("缺少 topicId 参数")
        }
        vm = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return TopicViewModel(topicId, App.INSTANCE) as T
            }
        }).get(TopicViewModel::class.java)
        return listOf(vm)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.loadingWrapState.observe(this) { loading ->
            if (loading) {
                vb.loadingLayout.update(LoadingWrapLayout.Status.LOADING)
            } else {
                vb.loadingLayout.update(LoadingWrapLayout.Status.DONE)
            }
        }
        vm.loadingDialog.observe(this) { loadingDialog.update(!it) }

        vb.replyRv.layoutManager = LinearLayoutManager(this)
        (vb.replyRv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        val delegate = LoadMoreDelegate(vb.replyRv) { vm.loadReplies(append = true, doReverse = false) }
        delegate.adapter.apply {
            register(Topic::class.java, TopicDetailsAdapter())
            register(Reply::class.java, ReplyAdapter())
            register(ReplyHeaderType::class.java, ReplyHeaderAdapter { vm.loadReplies(append = false, doReverse = true) })
        }
        vm.topicData.observe(this) {
            val (topic, append, hasMore, isOrder) = it
            val isFirst = delegate.items.isEmpty()
            if (isFirst || !append) {
                delegate.apply {
                    items.clear()
                    items.add(topic)
                    if (topic.replies.isNotEmpty()) {
                        items.add(ReplyHeaderType(isOrder))
                        items.addAll(topic.replies)
                    }
                    adapter.notifyDataSetChanged()
                }
            } else {
                delegate.apply {
                    adapter.notifyItemChanged(items.itemSize - 1)   // fix 最后一项 divider 不刷新的问题
                    val originSize = items.itemSize
                    items.addAll(topic.replies)
                    adapter.notifyItemRangeInserted(originSize, topic.replies.size);
                }
            }
            delegate.notifyLoadSuccess(hasMore)
        }

        vb.replyRv.addItemDecoration(
            object : LineDividerItemDecoration(
                this,
                height = 4f.dp().toInt()
            ) {
                override fun isSkip(position: Int): Boolean {
                    return false
                }
            }
        )
    }

}