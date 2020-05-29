package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class BasePageViewModel(application: Application) : BaseViewModel(application) {

    data class PageData(
        val dataList: List<Any>,
        val hasMore: Boolean
    )

    private val _pageLiveData = MutableLiveData<PageData>()

    val pageData: LiveData<PageData>
        get() = _pageLiveData

    private var currentPage = 1

    fun loadDataList(loadMore: Boolean) = request {
        if (loadMore && _pageLiveData.value == null) {
            throw RuntimeException("不允许首次直接进行 loadMore 操作")
        }
        val requestPage = if (!loadMore) 1 else currentPage + 1
        val result = providePageData(requestPage)
        val dataList = if (!loadMore) {
            result.dataList
        } else {
            _pageLiveData.value!!.dataList.toMutableList().apply {
                addAll(result.dataList)
            }
        }
        _pageLiveData.value = PageData(
            dataList, result.hasMore
        )
        currentPage = requestPage
    }

    abstract suspend fun providePageData(requestPage: Int): PageData

}