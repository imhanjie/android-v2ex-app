package com.imhanjie.v2ex.view.adapter

import com.imhanjie.v2ex.databinding.ItemNodeBinding
import com.imhanjie.v2ex.parser.model.TinyNode
import com.imhanjie.widget.recyclerview.base.BaseItemViewDelegate
import com.imhanjie.widget.recyclerview.base.VBViewHolder

class NodeItemAdapter : BaseItemViewDelegate<TinyNode, ItemNodeBinding>() {

    override fun bindTo(holder: VBViewHolder<ItemNodeBinding>, position: Int, item: TinyNode) {
        with(holder.vb) {
            tvNode.text = item.title
        }
    }

}