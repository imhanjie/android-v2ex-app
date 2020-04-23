package com.imhanjie.widget.recyclerview.loadmore

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import com.imhanjie.widget.databinding.WidgetItemLoadMoreBinding
import com.imhanjie.widget.recyclerview.base.BaseItemViewDelegate
import com.imhanjie.widget.recyclerview.base.VBViewHolder

class LoadMoreItemViewDelegate : BaseItemViewDelegate<FooterItem, WidgetItemLoadMoreBinding>() {

    public var retryBlock: (() -> Unit)? = null

    override fun bindTo(holder: VBViewHolder<WidgetItemLoadMoreBinding>, position: Int, item: FooterItem) {
        val vb = holder.vb
        vb.pbLoading.indeterminateDrawable.setColorFilter(Color.parseColor("#d7d9da"), PorterDuff.Mode.SRC_IN);
        when (item.type) {
            FooterType.HAS_MORE -> {
                showView(vb.loadingView, holder.itemView as ViewGroup)
            }
            FooterType.NO_MORE -> {
                showView(vb.viewNoMore, holder.itemView as ViewGroup)
            }
            FooterType.BAD_NETWORK -> {
                showView(vb.viewBadNetworkRetry, holder.itemView as ViewGroup)
                vb.viewBadNetworkRetry.setOnClickListener {
                    retryBlock?.invoke()
                }
            }
        }
    }

    private fun showView(view: View, parent: ViewGroup) {
        var v: View
        for (i in 0 until parent.childCount) {
            v = parent.getChildAt(i)
            if (view === v) {
                v.visibility = View.VISIBLE
            } else {
                v.visibility = View.GONE
            }
        }
    }

}