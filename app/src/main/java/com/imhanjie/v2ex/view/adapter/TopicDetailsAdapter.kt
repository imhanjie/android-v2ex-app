package com.imhanjie.v2ex.view.adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.api.model.Topic
import com.imhanjie.v2ex.databinding.ItemTopicDetailsBinding
import com.imhanjie.v2ex.view.MemberActivity
import com.imhanjie.v2ex.view.NodeActivity
import com.imhanjie.widget.recyclerview.base.BaseItemViewDelegate
import com.imhanjie.widget.recyclerview.base.VBViewHolder

class TopicDetailsAdapter : BaseItemViewDelegate<Topic, ItemTopicDetailsBinding>() {

    override fun bindTo(holder: VBViewHolder<ItemTopicDetailsBinding>, position: Int, item: Topic) {
        with(holder.vb) {
            Glide.with(root)
                .load(item.userAvatar)
                .placeholder(ContextCompat.getDrawable(ctx, R.drawable.default_avatar))
                .transform(CircleCrop())
                .into(ivUserAvatar)
            tvTitle.text = item.title
            tvUserName.text = item.userName
            tvTime.text = ctx.getString(R.string.topic_details_desc, item.createTime, item.click)
            tvNodeTitle.text = item.nodeTitle
            content.setRichContent(item.richContent)
            content.visibility = if (item.richContent.isEmpty()) View.GONE else View.VISIBLE

            tvNodeTitle.setOnClickListener {
                ctx.toActivity<NodeActivity>(
                    bundleOf(
                        "title" to item.nodeTitle,
                        "name" to item.nodeName
                    )
                )
            }

            ivUserAvatar.setOnClickListener {
                ctx.toActivity<MemberActivity>(bundleOf("userName" to item.userName))
            }
        }
    }

}