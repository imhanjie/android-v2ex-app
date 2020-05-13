package com.imhanjie.v2ex.view.adapter

import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.ItemReplyBinding
import com.imhanjie.v2ex.parser.model.Reply
import com.imhanjie.widget.recyclerview.base.BaseItemViewDelegate
import com.imhanjie.widget.recyclerview.base.VBViewHolder

class ReplyAdapter : BaseItemViewDelegate<Reply, ItemReplyBinding>() {

    override fun bindTo(holder: VBViewHolder<ItemReplyBinding>, position: Int, item: Reply) {
        with(holder.vb) {
            Glide.with(root)
                .load(item.userAvatar)
                .placeholder(ContextCompat.getDrawable(root.context, R.drawable.default_avatar))
                .transform(CircleCrop())
                .into(userAvatar)
            userName.text = item.userName
            time.text = item.time
            no.text = ctx.getString(R.string.reply_floor, item.no)
            content.setRichContent(item.content)
        }
    }

}