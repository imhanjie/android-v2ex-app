package com.imhanjie.v2ex.view

import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import com.drakeet.multitype.MultiTypeAdapter
import com.imhanjie.support.ext.dpi
import com.imhanjie.v2ex.BaseFragment
import com.imhanjie.v2ex.common.valueIsNull
import com.imhanjie.v2ex.databinding.FragmentBasePageBinding
import com.imhanjie.v2ex.vm.BasePageViewModel
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.widget.LoadingWrapLayout
import com.imhanjie.widget.recyclerview.SpaceItemDecoration
import com.imhanjie.widget.recyclerview.loadmore.LoadMoreDelegate

abstract class BasePageFragment<VM : BasePageViewModel> : BaseFragment<FragmentBasePageBinding>() {

    protected lateinit var vm: VM

    override fun initViewModels(): List<BaseViewModel> {
        vm = getViewModel()
        return listOf(vm)
    }

    override fun initViews() {
        if (autoLoadDataList() && vm.pageData.valueIsNull()) {
            vm.loadDataList(loadMore = false)
        }

        vb.loadingLayout.update(LoadingWrapLayout.Status.LOADING)
        vb.loadingLayout.retryCallback = {
            vb.loadingLayout.update(LoadingWrapLayout.Status.LOADING)
            vm.loadDataList(loadMore = false)
        }
        vb.swipeRefreshLayout.setOnRefreshListener {
            vm.loadDataList(loadMore = false)
        }
        vb.rv.addItemDecoration(SpaceItemDecoration(4.dpi, 0, 0))
        val delegate = LoadMoreDelegate(vb.rv) {
            vm.loadDataList(loadMore = true)
        }

        // 子类注册
        registerAdapter(delegate.adapter)
        vm.pageData.observe(viewLifecycleOwner) {
            val (dataList, hasMore) = it
            if (dataList.isEmpty()) {
                vb.loadingLayout.update(LoadingWrapLayout.Status.EMPTY, getEmptyTips())
                vb.swipeRefreshLayout.isRefreshing = false
            } else {
                vb.loadingLayout.update(LoadingWrapLayout.Status.DONE)
                delegate.apply {
                    val diffResult = DiffUtil.calculateDiff(getDiffCallback(items, dataList))
                    items.clear()
                    items.addAll(dataList)
                    diffResult.dispatchUpdatesTo(adapter)
                    notifyLoadSuccess(hasMore)
                }
                // 下拉刷新完回到顶部
                if (vb.swipeRefreshLayout.isRefreshing) {
                    vb.swipeRefreshLayout.isRefreshing = false
                    vb.rv.post {
                        vb.rv.smoothScrollToPosition(0)
                    }
                }
            }
        }
        vm.loadFailState.observe(this) { loadMore ->
            if (loadMore) {
                delegate.notifyLoadFailed()
            } else {
                vb.loadingLayout.update(LoadingWrapLayout.Status.FAIL)
            }
        }
    }

    open fun autoLoadDataList() = true

    abstract fun getViewModel(): VM

    abstract fun getDiffCallback(oldItems: List<Any>, newItems: List<Any>): DiffUtil.Callback

    abstract fun registerAdapter(adapter: MultiTypeAdapter)

    open fun getEmptyTips(): String? {
        return null
    }

}