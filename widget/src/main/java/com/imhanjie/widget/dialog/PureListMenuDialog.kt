package com.imhanjie.widget.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.imhanjie.widget.R
import com.imhanjie.widget.databinding.WidgetDialogListMenuBinding
import com.imhanjie.widget.databinding.WidgetItemListMenuBinding
import com.imhanjie.widget.recyclerview.base.BaseAdapter
import com.imhanjie.widget.todo.setDrawableStart

class PureListMenuDialog(ctx: Context) : BaseCustomDialog<WidgetDialogListMenuBinding>(ctx) {

    data class Item(
        val name: String,
        @DrawableRes val drawableRes: Int = 0,
        val onClickListener: ((dialog: Dialog) -> Unit)? = null
    )

    private val menus = mutableListOf<Item>()

    override val windowGravity: Int
        get() = Gravity.CENTER

    override val windowAnimation: Int
        get() = R.style.WidgetCenterDialogAnimation

    override fun initView(root: View) {
        vb.rvMenu.layoutManager = LinearLayoutManager(context)
        val adapter = object : BaseAdapter<WidgetItemListMenuBinding, Item>(menus) {
            override fun bindTo(vb: WidgetItemListMenuBinding, position: Int, item: Item) {
                vb.tvMenu.text = item.name
                vb.tvMenu.setDrawableStart(item.drawableRes)
            }
        }
        adapter.onItemClickListener = { _, item, _ ->
            dismiss()
            item.onClickListener?.invoke(this)
        }
        vb.rvMenu.adapter = adapter
        vb.rvMenu.clipToOutline = true
    }

    fun withCancelable(cancelable: Boolean): PureListMenuDialog {
        setCancelable(cancelable)
        return this
    }

    fun withMenuItem(menu: Item): PureListMenuDialog {
        this.menus.add(menu)
        return this
    }

    fun withMenuItems(menus: Collection<Item>): PureListMenuDialog {
        this.menus.clear()
        this.menus.addAll(menus)
        return this
    }

}