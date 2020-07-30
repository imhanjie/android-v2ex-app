package com.imhanjie.v2ex.view.adapter

import com.imhanjie.v2ex.databinding.ItemNodeTypeBinding
import com.imhanjie.widget.recyclerview.base.BaseItemViewDelegate
import com.imhanjie.widget.recyclerview.base.VBViewHolder

class NodeTypeAdapter : BaseItemViewDelegate<ItemNodeTypeBinding, String>() {

    override fun bindTo(holder: VBViewHolder<ItemNodeTypeBinding>, position: Int, item: String) {
        with(holder.vb) {
            tvNode.text = item
        }
    }

}