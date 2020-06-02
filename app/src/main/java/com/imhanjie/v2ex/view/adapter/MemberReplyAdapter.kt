package com.imhanjie.v2ex.view.adapter

import com.imhanjie.v2ex.api.model.MemberReplies
import com.imhanjie.v2ex.databinding.ItemMemberReplyBinding
import com.imhanjie.widget.recyclerview.base.BaseItemViewDelegate
import com.imhanjie.widget.recyclerview.base.VBViewHolder

class MemberReplyAdapter : BaseItemViewDelegate<MemberReplies.Item, ItemMemberReplyBinding>() {

    override fun bindTo(holder: VBViewHolder<ItemMemberReplyBinding>, position: Int, item: MemberReplies.Item) {
        with(holder.vb) {
            tvTitle.setRichContent(item.titleRichContent)
            tvReply.setRichContent(item.replyRichContent)
            tvTime.text = item.createTime
        }
    }

}