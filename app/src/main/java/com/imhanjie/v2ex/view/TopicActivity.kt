package com.imhanjie.v2ex.view

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.imhanjie.support.ext.dp
import com.imhanjie.support.ext.getResColor
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.databinding.ActivityTopicBinding
import com.imhanjie.v2ex.parser.model.Reply
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

        vm = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>) = TopicViewModel(topicId) as T
        }).get(TopicViewModel::class.java)
        vm.error.observe(this) { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }

        vb.loadingLayout.hide()

        vm.loadingState.observe(this) { loading ->
            if (loading) {
                vb.loadingLayout.show()
            } else {
                vb.loadingLayout.hide()
            }
        }

        vb.replyRv.layoutManager = LinearLayoutManager(this)
        val delegate = LoadMoreDelegate(vb.replyRv) { vm.loadMore() }
        delegate.adapter.register(Reply::class.java, ReplyAdapter())
        vm.replies.observe(this) {
            val (newReplies, fromLoadMore, hasMore) = it
            if (!fromLoadMore) {
                delegate.items.clear()
                delegate.items.addAll(newReplies)
                delegate.adapter.notifyDataSetChanged()
            } else {
                delegate.adapter.notifyItemChanged(delegate.items.itemSize - 1)
                val originSize = delegate.items.itemSize
                delegate.items.addAll(newReplies)
                delegate.adapter.notifyItemRangeInserted(originSize, newReplies.size);
            }
            delegate.notifyLoadSuccess(hasMore)
        }

        vb.replyRv.addItemDecoration(
            object : LineDividerItemDecoration(
                this,
                color = getResColor(com.imhanjie.widget.R.color.widget_divider),
                height = 1,
                marginStart = 59f.dp().toInt(),
                backgroundColor = Color.WHITE
            ) {
                override fun isSkip(position: Int): Boolean {
                    return position == delegate.items.itemSize - 1
                }
            }

        )

    }

}