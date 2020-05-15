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
                .placeholder(ContextCompat.getDrawable(root.context, R.drawable.default_avatar))
                .transform(CircleCrop())
                .into(userAvatar)
            title.text = item.title
            userName.text = item.userName
            if (item.isTop) {
                time.text = ctx.getString(R.string.topic_item_desc_with_top, item.latestReplyTime, item.replies)
            } else {
                time.text = ctx.getString(R.string.topic_item_desc, item.latestReplyTime, item.replies)
            }
            nodeTitle.text = item.nodeTitle
            nodeTitle.setOnClickListener {
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