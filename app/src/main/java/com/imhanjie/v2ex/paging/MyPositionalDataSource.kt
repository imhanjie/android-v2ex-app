package com.imhanjie.v2ex.paging

import androidx.paging.PositionalDataSource

class MyPositionalDataSource : PositionalDataSource<Student>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Student>) {
        TODO("Not yet implemented")
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Student>) {
        TODO("Not yet implemented")
    }

}