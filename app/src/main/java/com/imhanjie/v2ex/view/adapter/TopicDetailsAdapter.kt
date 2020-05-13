package com.imhanjie.v2ex.view.adapter

import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.ItemTopicDetailsBinding
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.widget.recyclerview.base.BaseItemViewDelegate
import com.imhanjie.widget.recyclerview.base.VBViewHolder

class TopicDetailsAdapter : BaseItemViewDelegate<Topic, ItemTopicDetailsBinding>() {

    override fun bindTo(holder: VBViewHolder<ItemTopicDetailsBinding>, position: Int, item: Topic) {
        val vb = holder.vb
        Glide.with(vb.root)
            .load(item.userAvatar)
            .placeholder(ContextCompat.getDrawable(vb.root.context, R.drawable.default_avatar))
            .transform(CircleCrop())
            .into(vb.userAvatar)
        vb.title.text = item.title
        vb.userName.text = item.userName
        vb.time.text = ctx.getString(R.string.topic_details_desc, item.createTime, item.click)
        vb.nodeTitle.text = item.nodeTitle
        vb.content.setRichContent(item.richContent)
        vb.content.visibility = if (item.richContent.isEmpty()) View.GONE else View.VISIBLE
    }

}