package com.imhanjie.v2ex.view

import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.ItemReplyHeaderBinding
import com.imhanjie.v2ex.model.ReplyHeaderType
import com.imhanjie.widget.recyclerview.base.BaseItemViewDelegate
import com.imhanjie.widget.recyclerview.base.VBViewHolder

class ReplyHeaderAdapter(
    private val reverseBlock: (() -> Unit)? = {}
) : BaseItemViewDelegate<ReplyHeaderType, ItemReplyHeaderBinding>() {

    override fun bindTo(holder: VBViewHolder<ItemReplyHeaderBinding>, position: Int, item: ReplyHeaderType) {
        val vb = holder.vb
        vb.reverse.setText(if (item.isOrder) R.string.reply_order else R.string.reply_reverse_order)
        vb.reverse.setOnClickListener {
            reverseBlock?.invoke()
        }
    }

}