package com.imhanjie.widget.recyclerview.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.drakeet.multitype.ItemViewDelegate
import com.imhanjie.widget.common.getVBClass

abstract class BaseItemViewDelegate<VB : ViewBinding, T> : ItemViewDelegate<T, VBViewHolder<VB>>() {

    public var onItemClickListener: ((holder: VBViewHolder<VB>, item: T, position: Int) -> Unit)? = null
    public var onItemLongClickListener: ((holder: VBViewHolder<VB>, item: T, position: Int) -> Boolean)? = null
    protected lateinit var ctx: Context

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(context: Context, parent: ViewGroup): VBViewHolder<VB> {
        val vbClass = getVBClass<VB>(javaClass)
        val method = vbClass.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        val vb = method.invoke(null, LayoutInflater.from(parent.context), parent, false) as VB
        ctx = vb.root.context
        return VBViewHolder(vb)
    }

    override fun onBindViewHolder(holder: VBViewHolder<VB>, item: T) {
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(holder, item, holder.adapterPosition)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener?.invoke(holder, item, holder.adapterPosition) ?: false
        }
        bindTo(holder, holder.adapterPosition, item)
    }

    /**
     * override by subclass
     */
    abstract fun bindTo(holder: VBViewHolder<VB>, position: Int, item: T)


}