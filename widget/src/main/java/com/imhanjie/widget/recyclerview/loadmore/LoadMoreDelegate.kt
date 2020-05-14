package com.imhanjie.widget.recyclerview.loadmore

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

class LoadMoreDelegate(
    private val recyclerView: RecyclerView,
    private val loadMoreListener: () -> Unit
) {

    companion object {
        private const val VISIBLE_THRESHOLD = 1
    }

    val items: LoadMoreItems = LoadMoreItems()
    val adapter: LoadMoreMultiTypeAdapter

    private var isLoading = false
    private var hasMore = true

    init {
        adapter = LoadMoreMultiTypeAdapter(items)
        adapter.loadMoreIvd.retryBlock = ::triggerLoadMore
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        (recyclerView.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        val scrollListener = EndlessScrollListener(recyclerView.layoutManager as LinearLayoutManager)
        recyclerView.addOnScrollListener(scrollListener)
        recyclerView.adapter = adapter
    }

    /**
     * 通知加载成功
     *
     * @param hasMore 是否还有更多数据
     */
    public fun notifyLoadSuccess(hasMore: Boolean) {
        this.isLoading = false
        this.hasMore = hasMore
        items.setFooterType(if (hasMore) FooterType.HAS_MORE else FooterType.NO_MORE)
        recyclerView.post { adapter.notifyItemChanged(items.footerPosition) }
        /*
        当调用方通知我们一次数据加载完成后，并且告诉我们还有更多数据，此时我们需要判断一下数据是否过少出现不足一屏幕的情况，
        如果数据不足一屏，则不会出现滑动的场景，则滑动加载更多永远不会被触发，但是实际情况是还有数据的，所以我们则再次
        继续自动触发 loadMore() 方法让调用方继续请求数据。
         */
        if (hasMore) {
            recyclerView.post {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount // 屏幕上可见的 item 数量
                val totalItemCount = layoutManager.itemCount // 总 item 数量
                val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition() // 最后一个完全显示出来的 item 的位置
                if (lastVisiblePosition >= totalItemCount - 1 && totalItemCount <= visibleItemCount) {
                    // 数据不足一屏
                    triggerLoadMore()
                }
            }
        }
    }

    /**
     * 通知加载失败
     */
    fun notifyLoadFailed() {
        isLoading = false
        items.setFooterType(FooterType.BAD_NETWORK)
        recyclerView.post { adapter.notifyItemChanged(items.footerPosition) }
    }

    /**
     * 触发加载更多
     */
    private fun triggerLoadMore() {
        isLoading = true
        if (items.getFooterType() != FooterType.HAS_MORE) {
            items.setFooterType(FooterType.HAS_MORE)
            recyclerView.post {
                adapter.notifyItemChanged(items.footerPosition)
            }
        }
        loadMoreListener.invoke()
    }

    private val canLoadMoreWhenScrolling
        get() = hasMore && !isLoading

    private inner class EndlessScrollListener constructor(
        private val layoutManager: LinearLayoutManager
    ) : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy <= 0) {
                return
            }
            val itemCount = layoutManager.itemCount
            val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
            if (lastVisiblePosition >= itemCount - VISIBLE_THRESHOLD) {
                if (canLoadMoreWhenScrolling) {
                    triggerLoadMore()
                }
            }
        }

    }

}