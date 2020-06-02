package com.imhanjie.v2ex.view.adapter

import androidx.core.os.bundleOf
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.api.model.TopicItem
import com.imhanjie.v2ex.databinding.ItemMemberTopicBinding
import com.imhanjie.v2ex.view.NodeActivity
import com.imhanjie.widget.recyclerview.base.BaseItemViewDelegate
import com.imhanjie.widget.recyclerview.base.VBViewHolder

class MemberTopicAdapter : BaseItemViewDelegate<TopicItem, ItemMemberTopicBinding>() {

    override fun bindTo(holder: VBViewHolder<ItemMemberTopicBinding>, position: Int, item: TopicItem) {
        with(holder.vb) {
            tvTitle.text = item.title
            if (item.isTop) {
                tvTime.text = ctx.getString(R.string.topic_item_desc_with_top, item.latestReplyTime, item.replies)
            } else {
                tvTime.text = ctx.getString(R.string.topic_item_desc, item.latestReplyTime, item.replies)
            }
            tvNodeTitle.text = item.nodeTitle
            tvNodeTitle.setOnClickListener {
                ctx.toActivity<NodeActivity>(
                    bundleOf(
                        "title" to item.nodeTitle,
                        "name" to item.nodeName
                    )
                )
            }
        }
    }

}