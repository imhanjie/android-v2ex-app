package com.imhanjie.v2ex.view.adapter

import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.ItemReplyHeaderBinding
import com.imhanjie.v2ex.model.ReplyHeaderType
import com.imhanjie.widget.recyclerview.base.BaseItemViewDelegate
import com.imhanjie.widget.recyclerview.base.VBViewHolder

class ReplyHeaderAdapter(
    private val reverseBlock: (() -> Unit)? = {}
) : BaseItemViewDelegate<ItemReplyHeaderBinding, ReplyHeaderType>() {

    override fun bindTo(holder: VBViewHolder<ItemReplyHeaderBinding>, position: Int, item: ReplyHeaderType) {
        with(holder.vb) {
            tvReverse.setText(if (item.isOrder) R.string.reply_order else R.string.reply_reverse_order)
            tvReverse.setOnClickListener {
                reverseBlock?.invoke()
            }
        }
    }

}