package com.imhanjie.v2ex.view.adapter

import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.ItemTopicBinding
import com.imhanjie.v2ex.parser.model.TopicItem
import com.imhanjie.v2ex.view.NodeActivity
import com.imhanjie.widget.recyclerview.base.BaseItemViewDelegate
import com.imhanjie.widget.recyclerview.base.VBViewHolder

class TopicAdapter : BaseItemViewDelegate<TopicItem, ItemTopicBinding>() {

    override fun bindTo(holder: VBViewHolder<ItemTopicBinding>, position: Int, item: TopicItem) {
        with(holder.vb) {
            Glide.with(root)
                .load(item.userAvatar)
                .placeholder(ContextCompat.getDrawable(ctx, R.drawable.default_avatar))
                .transform(CircleCrop())
                .into(ivUserAvatar)
            tvTitle.text = item.title
            tvUserName.text = item.userName
            if (item.isTop) {
                tvTime.text = ctx.getString(R.string.topic_item_desc_with_top, item.latestReplyTime, item.replies)
            } else {
                tvTime.text = ctx.getString(R.string.topic_item_desc, item.latestReplyTime, item.replies)
            }
            tvNodeTitle.text = item.nodeTitle
            tvNodeTitle.setOnClickListener {
                ctx.toActivity<NodeActivity>(
                    mapOf(
                        "title" to item.nodeTitle,
                        "name" to item.nodeName
                    )
                )
            }
        }
    }

}