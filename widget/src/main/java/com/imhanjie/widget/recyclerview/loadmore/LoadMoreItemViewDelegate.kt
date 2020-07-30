package com.imhanjie.widget.recyclerview.loadmore

import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.imhanjie.widget.R
import com.imhanjie.widget.databinding.WidgetItemLoadMoreBinding
import com.imhanjie.widget.recyclerview.base.BaseItemViewDelegate
import com.imhanjie.widget.recyclerview.base.VBViewHolder

class LoadMoreItemViewDelegate : BaseItemViewDelegate<WidgetItemLoadMoreBinding, FooterItem>() {

    public var retryBlock: (() -> Unit)? = null

    override fun bindTo(holder: VBViewHolder<WidgetItemLoadMoreBinding>, position: Int, item: FooterItem) {
        val vb = holder.vb
        vb.pbLoading.apply {
            indeterminateDrawable.setColorFilter(ContextCompat.getColor(context, R.color.widget_loading), PorterDuff.Mode.SRC_IN)
        }
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