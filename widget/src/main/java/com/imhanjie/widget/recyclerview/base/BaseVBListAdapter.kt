package com.imhanjie.widget.recyclerview.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseVBListAdapter<T, VB : ViewBinding>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, VBViewHolder<VB>>(diffCallback) {

    public var onItemClickListener: ((holder: VBViewHolder<VB>, item: T, position: Int) -> Unit)? = null
    public var onItemLongClickListener: ((holder: VBViewHolder<VB>, item: T, position: Int) -> Boolean)? = null
    protected lateinit var ctx: Context

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VBViewHolder<VB> {
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz: Class<VB> = type.actualTypeArguments[1] as Class<VB>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        val vb = method.invoke(null, LayoutInflater.from(parent.context), parent, false) as VB
        ctx = vb.root.context
        return VBViewHolder(vb)
    }

    override fun onBindViewHolder(holder: VBViewHolder<VB>, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(holder, getItem(position), position)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener?.invoke(holder, getItem(position), position) ?: false
        }
        bindTo(holder.vb, position, getItem(position))
    }

    /**
     * override by subclass
     */
    abstract fun bindTo(vb: VB, position: Int, item: T)

}