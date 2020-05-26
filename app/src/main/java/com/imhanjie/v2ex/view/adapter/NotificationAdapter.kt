package com.imhanjie.v2ex.view.adapter

import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.api.model.Notifications
import com.imhanjie.v2ex.databinding.ItemNotificaitonBinding
import com.imhanjie.widget.recyclerview.base.BaseItemViewDelegate
import com.imhanjie.widget.recyclerview.base.VBViewHolder

class NotificationAdapter : BaseItemViewDelegate<Notifications.Item, ItemNotificaitonBinding>() {

    override fun bindTo(holder: VBViewHolder<ItemNotificaitonBinding>, position: Int, item: Notifications.Item) {
        with(holder.vb) {
            Glide.with(root)
                .load(item.userAvatar)
                .placeholder(ContextCompat.getDrawable(root.context, R.drawable.default_avatar))
                .transform(CircleCrop())
                .into(ivUserAvatar)
            tvTitle.setRichContent(item.titleRichContent)
            tvReply.setRichContent(item.replyRichContent)
            tvTime.text = item.createTime

            tvReply.visibility = if (item.replyRichContent.isEmpty()) View.GONE else View.VISIBLE
        }
    }

}