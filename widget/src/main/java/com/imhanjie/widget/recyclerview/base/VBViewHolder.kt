package com.imhanjie.widget.recyclerview.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class VBViewHolder<VB : ViewBinding>(val vb: VB) : RecyclerView.ViewHolder(vb.root)