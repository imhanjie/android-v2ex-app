package com.imhanjie.v2ex.view.adapter

import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.api.model.Topic
import com.imhanjie.v2ex.databinding.ItemSubtleBinding
import com.imhanjie.widget.recyclerview.base.BaseItemViewDelegate
import com.imhanjie.widget.recyclerview.base.VBViewHolder

class SubtleAdapter : BaseItemViewDelegate<ItemSubtleBinding, Topic.Subtle>() {

    override fun bindTo(holder: VBViewHolder<ItemSubtleBinding>, position: Int, item: Topic.Subtle) {
        with(holder.vb) {
            tvTitle.text = ctx.getString(R.string.topic_subtle_title, item.no, item.createTime)
            tvContent.setRichContent(item.richContent)
        }
    }

}