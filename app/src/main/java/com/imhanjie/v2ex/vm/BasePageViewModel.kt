package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.common.extension.NonStickyLiveData
import com.imhanjie.v2ex.common.extension.valueIsNull

abstract class BasePageViewModel(application: Application) : BaseViewModel(application) {

    data class PageData(
        val dataList: List<Any>,
        val hasMore: Boolean
    )

    private val _pageLiveData = MutableLiveData<PageData>()

    val pageData: LiveData<PageData>
        get() = _pageLiveData

    private val _loadFailState = NonStickyLiveData<Boolean>()

    val loadFailState: LiveData<Boolean>
        get() = _loadFailState

    private var currentPage = 1
    private var requestPage = 1

    fun loadDataList(loadMore: Boolean) = request(
        onError = {
            _loadFailState.value = requestPage > 1
        }
    ) {
        if (loadMore && _pageLiveData.valueIsNull()) {
            throw RuntimeException("不允许首次直接进行 loadMore 操作")
        }
        requestPage = if (!loadMore) 1 else currentPage + 1
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

    /**
     * 过滤 item
     */
    protected fun filterItem(predicate: (Any) -> Boolean) {
        _pageLiveData.value?.let {
            _pageLiveData.value = it.copy(
                dataList = it.dataList.toMutableList().filter(predicate)
            )
        }
    }

    abstract suspend fun providePageData(requestPage: Int): PageData

}