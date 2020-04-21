package com.imhanjie.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class VBListAdapter<T, VB : ViewBinding>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, CommonViewHolder<VB>>(diffCallback) {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder<VB> {
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz: Class<VB> = type.actualTypeArguments[1] as Class<VB>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        val vb = method.invoke(null, LayoutInflater.from(parent.context), parent, false) as VB
        return CommonViewHolder(vb)
    }

    override fun onBindViewHolder(holder: CommonViewHolder<VB>, position: Int) {
        bindTo(holder.vb, position, getItem(position))
    }

    /**
     * override by subclass
     */
    abstract fun bindTo(vb: VB, position: Int, item: T)

}

class CommonViewHolder<VB : ViewBinding>(val vb: VB) : RecyclerView.ViewHolder(vb.root)