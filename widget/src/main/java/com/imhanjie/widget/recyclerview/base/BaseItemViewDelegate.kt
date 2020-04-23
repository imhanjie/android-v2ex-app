package com.imhanjie.widget.recyclerview.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.drakeet.multitype.ItemViewDelegate
import java.lang.reflect.ParameterizedType

abstract class BaseItemViewDelegate<T, VB : ViewBinding> : ItemViewDelegate<T, VBViewHolder<VB>>() {

    public var onItemClickListener: ((holder: VBViewHolder<VB>, item: T, position: Int) -> Unit)? = null
    public var onItemLongClickListener: ((holder: VBViewHolder<VB>, item: T, position: Int) -> Boolean)? = null

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(context: Context, parent: ViewGroup): VBViewHolder<VB> {
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz: Class<VB> = type.actualTypeArguments[1] as Class<VB>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        val vb = method.invoke(null, LayoutInflater.from(parent.context), parent, false) as VB
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