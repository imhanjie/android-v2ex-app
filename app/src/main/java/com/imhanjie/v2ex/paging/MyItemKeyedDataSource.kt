package com.imhanjie.v2ex.paging

import androidx.paging.ItemKeyedDataSource

class MyItemKeyedDataSource : ItemKeyedDataSource<Int, Student>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Student>
    ) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Student>) {
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Student>) {
    }

    override fun getKey(item: Student): Int {
        TODO("Not yet implemented")
    }
}