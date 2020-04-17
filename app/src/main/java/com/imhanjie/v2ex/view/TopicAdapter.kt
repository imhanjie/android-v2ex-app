package com.imhanjie.v2ex.view

import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.imhanjie.v2ex.databinding.ItemTopicBinding
import com.imhanjie.v2ex.parser.model.TopicItem

class TopicAdapter : com.imhanjie.widget.VBListAdapter<TopicItem, ItemTopicBinding>(TopicDiffCallback()) {

    override fun bindTo(view: ItemTopicBinding, position: Int, item: TopicItem) {
        Glide.with(view.root)
            .load(item.userAvatar)
            .transform(CircleCrop())
            .into(view.userAvatar)
        view.title.text = item.title
        view.userName.text = item.userName
        view.time.text = item.latestReplyTime + "  •  " + item.replies + " 条回复"
        view.nodeName.text = item.nodeName
    }

}

class TopicDiffCallback : DiffUtil.ItemCallback<TopicItem>() {

    override fun areItemsTheSame(oldItem: TopicItem, newItem: TopicItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TopicItem, newItem: TopicItem) = oldItem == newItem

}