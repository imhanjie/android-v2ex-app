package com.imhanjie.v2ex.view

import android.annotation.SuppressLint
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.imhanjie.v2ex.databinding.ItemTopicDetailsBinding
import com.imhanjie.v2ex.parser.model.Topic
import com.imhanjie.widget.recyclerview.base.BaseItemViewDelegate
import com.imhanjie.widget.recyclerview.base.VBViewHolder

class TopicDetailsAdapter : BaseItemViewDelegate<Topic, ItemTopicDetailsBinding>() {

    @SuppressLint("ResourceType")
    override fun bindTo(holder: VBViewHolder<ItemTopicDetailsBinding>, position: Int, item: Topic) {
        val vb = holder.vb
        Glide.with(vb.root)
            .load(item.userAvatar)
            .transform(CircleCrop())
            .into(vb.userAvatar)
        vb.title.text = item.title
        vb.userName.text = item.userName
        vb.time.text = "${item.createTime}  •  ${item.click} 次点击"
        vb.nodeTitle.text = item.nodeTitle
        vb.content.setRichContent(item.content)
        vb.content.visibility = if (item.content.isEmpty()) View.GONE else View.VISIBLE
    }

}