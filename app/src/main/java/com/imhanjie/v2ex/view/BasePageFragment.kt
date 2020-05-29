package com.imhanjie.v2ex.view

import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import com.drakeet.multitype.MultiTypeAdapter
import com.imhanjie.support.e
import com.imhanjie.support.ext.dpi
import com.imhanjie.v2ex.BaseFragment
import com.imhanjie.v2ex.databinding.FragmentBasePageBinding
import com.imhanjie.v2ex.vm.BasePageViewModel
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.widget.LineDividerItemDecoration
import com.imhanjie.widget.LoadingWrapLayout
import com.imhanjie.widget.recyclerview.loadmore.LoadMoreDelegate

abstract class BasePageFragment<VM : BasePageViewModel> : BaseFragment<FragmentBasePageBinding>() {

    protected lateinit var vm: VM

    override fun getViewModels(): List<BaseViewModel> {
        vm = getViewModel()
        return listOf(vm)
    }

    override fun initViews() {
        if (autoLoadDataList()) {
            vm.loadDataList(loadMore = false)
        }

        vb.loadingLayout.update(LoadingWrapLayout.Status.LOADING)
        vb.swipeRefreshLayout.setOnRefreshListener {
            vm.loadDataList(loadMore = false)
        }
        vb.rv.addItemDecoration(LineDividerItemDecoration(requireContext(), height = 4.dpi))
        val delegate = LoadMoreDelegate(vb.rv) {
            vm.loadDataList(loadMore = true)
        }

        // 子类注册
        registerAdapter(delegate.adapter)
        e("注册")
        vm.pageData.observe(this) {
            vb.loadingLayout.update(LoadingWrapLayout.Status.DONE)
            vb.swipeRefreshLayout.isRefreshing = false
            val (dataList, hasMore) = it
            delegate.apply {
                val diffResult = DiffUtil.calculateDiff(getDiffCallback(items, dataList))
                items.clear()
                items.addAll(dataList)
                diffResult.dispatchUpdatesTo(adapter)
                notifyLoadSuccess(hasMore)
            }
        }
    }

    open fun autoLoadDataList() = true

    abstract fun getViewModel(): VM

    abstract fun getDiffCallback(oldItems: List<Any>, newItems: List<Any>): DiffUtil.Callback

    abstract fun registerAdapter(adapter: MultiTypeAdapter)

}