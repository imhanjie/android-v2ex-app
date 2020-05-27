package com.imhanjie.v2ex.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.imhanjie.support.ext.dp
import com.imhanjie.support.ext.toast
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.api.model.Reply
import com.imhanjie.v2ex.api.model.Topic
import com.imhanjie.v2ex.databinding.ActivityTopicBinding
import com.imhanjie.v2ex.model.ReplyHeaderType
import com.imhanjie.v2ex.view.adapter.ReplyAdapter
import com.imhanjie.v2ex.view.adapter.ReplyHeaderAdapter
import com.imhanjie.v2ex.view.adapter.SubtleAdapter
import com.imhanjie.v2ex.view.adapter.TopicDetailsAdapter
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.TopicViewModel
import com.imhanjie.widget.LineDividerItemDecoration
import com.imhanjie.widget.LoadingWrapLayout
import com.imhanjie.widget.dialog.PureAlertDialog
import com.imhanjie.widget.dialog.PureListMenuDialog
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
                return TopicViewModel(topicId, application) as T
            }
        }).get(TopicViewModel::class.java)
        return listOf(vm)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vb.loadingLayout.update(LoadingWrapLayout.Status.LOADING)
        vb.topBar.setOnClickListener { vb.replyRv.smoothScrollToPosition(0) }
        vb.topBar.setOnRightClickListener(View.OnClickListener { showTopicMenuDialog() })
        vm.loading.observe(this) { loadingDialog.update(!it) }

        val delegate = LoadMoreDelegate(vb.replyRv) { vm.loadReplies(append = true, doReverse = false) }
        delegate.adapter.apply {
            register(Topic::class.java, TopicDetailsAdapter())
            register(Topic.Subtle::class.java, SubtleAdapter())
            register(Reply::class.java, ReplyAdapter().apply {
                onItemClickListener = { holder, item, position ->
                    showReplyMenuDialog(item)
                }
            })
            register(ReplyHeaderType::class.java, ReplyHeaderAdapter {
                vm.loadReplies(append = false, doReverse = true)
            })
        }

        vb.replyRv.addItemDecoration(
            object : LineDividerItemDecoration(
                this,
                height = 4.dp.toInt()
            ) {
                override fun isSkip(position: Int): Boolean {
                    return position == 0 || delegate.items[position] is Topic.Subtle
                }
            }
        )

        vm.topic.observe(this) {
            val (topic, append, hasMore, isOrder) = it

            vb.loadingLayout.update(LoadingWrapLayout.Status.DONE)
            if (!topic.isMyTopic) {
                vb.topBar.setRightVisibility(View.VISIBLE)
            }

            delegate.apply {
                val isFirst = items.isEmpty()
                if (isFirst || !append) {
                    items.clear()
                    items.add(topic)
                    items.addAll(topic.subtleList)
                    if (topic.replies.isNotEmpty()) {
                        items.add(ReplyHeaderType(isOrder))
                        items.addAll(topic.replies)
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    adapter.notifyItemChanged(items.itemSize - 1)   // fix 最后一项 divider 不刷新的问题
                    val originSize = items.itemSize
                    items.addAll(topic.replies)
                    adapter.notifyItemRangeInserted(originSize, topic.replies.size);
                }
                notifyLoadSuccess(hasMore)
            }
        }
        vm.thankReply.observe(this) {
            toast(R.string.tips_thank_reply_success)
            delegate.adapter.notifyItemChanged(delegate.items.indexOf(it))
        }
        vm.ignoreTopicState.observe(this) {
            toast(R.string.tips_ignore_topic_success)
            finish()
            globalViewModel.ignoreTopic.value = vm.topicId
        }
        vm.favoriteTopicState.observe(this) { favorite ->
            toast(if (favorite) R.string.tips_favorite_success else R.string.tips_un_favorite_success)
        }
    }

    private fun showReplyMenuDialog(item: Reply) {
        PureListMenuDialog(this).apply {
            withCancelable(true)
            if (!item.thanked) {
                withMenuItem(PureListMenuDialog.Item(getString(R.string.menu_thank), onClickListener = {
                    showThankDialog(item)
                }))
            }
            withMenuItem(PureListMenuDialog.Item(getString(R.string.menu_reply)))
            show()
        }
    }

    private fun showThankDialog(item: Reply) {
        PureAlertDialog(this)
            .withTitle("感谢")
            .withContent("确认花费 10 个铜币向 @${item.userName} 的这条回复发送感谢？")
            .withPositiveClick {
                vm.thankReply(item)
            }
            .show()
    }

    private fun showTopicMenuDialog() {
        PureListMenuDialog(this).apply {
            withCancelable(true)
            withMenuItem(PureListMenuDialog.Item(getString(R.string.menu_ignore_topic), onClickListener = {
                showIgnoreTopicDialog()
            }))
            if (!vm.topicIsFavorite()) {
                withMenuItem(PureListMenuDialog.Item(getString(R.string.menu_favorite_topic), onClickListener = {
                    vm.favoriteTopic()
                }))
            } else {
                withMenuItem(PureListMenuDialog.Item(getString(R.string.menu_un_favorite_topic), onClickListener = {
                    vm.unFavoriteTopic()
                }))
            }
            show()
        }
    }

    private fun showIgnoreTopicDialog() {
        PureAlertDialog(this)
            .withTitle("忽略主题")
            .withContent("确定不想再看到这个主题？")
            .withPositiveClick { vm.ignoreTopic() }
            .show()
    }

}