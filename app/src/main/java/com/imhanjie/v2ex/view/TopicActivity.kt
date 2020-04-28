package com.imhanjie.v2ex.view

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imhanjie.support.ext.dp
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.databinding.ActivityTopicBinding
import com.imhanjie.v2ex.parser.model.Reply
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.v2ex.vm.TopicViewModel
import com.imhanjie.widget.LineDividerItemDecoration
import com.imhanjie.widget.recyclerview.loadmore.LoadMoreDelegate

class TopicActivity : BaseActivity<ActivityTopicBinding>() {

    private lateinit var vm: TopicViewModel

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val topicId: Long = intent.getLongExtra("topicId", -1)
        if (topicId < 0) {
            throw IllegalArgumentException("error topicId")
        }

        vm = ViewModelProvider(this).get(TopicViewModel::class.java)
        vm.topicId = topicId

        vm.error.observe(this) { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        vm.loadingState.observe(this) { vb.loadingLayout.update(!it) }

        vb.replyRv.layoutManager = LinearLayoutManager(this)
        (vb.replyRv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        val delegate = LoadMoreDelegate(vb.replyRv) { vm.loadMore() }
        delegate.adapter.apply {
            register(Topic::class.java, TopicDetailsAdapter())
            register(Reply::class.java, ReplyAdapter())
        }
        vm.topic.observe(this) {
            val (topic, fromLoadMore, hasMore) = it
            if (!fromLoadMore) {
                delegate.apply {
                    items.clear()
                    items.add(topic)
                    items.addAll(topic.replies)
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