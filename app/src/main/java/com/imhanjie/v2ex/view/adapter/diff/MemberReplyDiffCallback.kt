package com.imhanjie.v2ex.view.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import com.imhanjie.v2ex.api.model.MemberReplies

class MemberReplyDiffCallback(
    private val oldItems: List<Any>,
    private val newItems: List<Any>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return if (oldItem.javaClass != newItem.javaClass) {
            false
        } else if (oldItem is MemberReplies.Item && newItem is MemberReplies.Item) {
            oldItem == newItem
        } else {
            true
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }

}