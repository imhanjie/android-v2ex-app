package com.imhanjie.v2ex.view

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.ItemTopicBinding
import com.imhanjie.v2ex.parser.model.TopicItem
import com.imhanjie.widget.recyclerview.base.BaseVBListAdapter

class TopicAdapter : BaseVBListAdapter<TopicItem, ItemTopicBinding>(TopicDiffCallback()) {

    override fun bindTo(vb: ItemTopicBinding, position: Int, item: TopicItem) {
        Glide.with(vb.root)
            .load(item.userAvatar)
            .placeholder(ContextCompat.getDrawable(vb.root.context, R.drawable.default_avatar))
            .transform(CircleCrop())
            .into(vb.userAvatar)
        vb.title.text = item.title
        vb.userName.text = item.userName
        if (item.isTop) {
            vb.time.text = "置顶  •  ${item.latestReplyTime}  •  ${item.replies} 条回复"
        } else {
            vb.time.text = "${item.latestReplyTime}  •  ${item.replies} 条回复"
        }
        vb.nodeTitle.text = item.nodeTitle
    }

}

class TopicDiffCallback : DiffUtil.ItemCallback<TopicItem>() {

    override fun areItemsTheSame(oldItem: TopicItem, newItem: TopicItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TopicItem, newItem: TopicItem) = oldItem == newItem

}