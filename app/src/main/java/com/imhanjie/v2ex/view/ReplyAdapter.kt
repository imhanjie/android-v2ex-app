package com.imhanjie.v2ex.view

import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.imhanjie.v2ex.databinding.ItemReplyBinding
import com.imhanjie.v2ex.parser.model.Reply
import com.imhanjie.widget.recyclerview.base.BaseVBListAdapter

class ReplyAdapter : BaseVBListAdapter<Reply, ItemReplyBinding>(ReplyDiffCallback()) {

    override fun bindTo(vb: ItemReplyBinding, position: Int, item: Reply) {
        Glide.with(vb.root)
            .load(item.userAvatar)
            .transform(CircleCrop())
            .into(vb.userAvatar)
        vb.userName.text = item.userName
        vb.time.text = item.time
        vb.content.text = item.content
        vb.no.text = "#${item.no}"
    }

}

class ReplyDiffCallback : DiffUtil.ItemCallback<Reply>() {

    override fun areItemsTheSame(oldItem: Reply, newItem: Reply) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Reply, newItem: Reply) = oldItem == newItem

}