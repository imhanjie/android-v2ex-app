package com.imhanjie.v2ex.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class StudentDataSourceFactory : DataSource.Factory<Int, Student>() {

    private val sourceLiveData = MutableLiveData<MyPageKeyedDataSource>()

    override fun create(): DataSource<Int, Student> {
        val dataSource = MyPageKeyedDataSource()
        sourceLiveData.postValue(dataSource)
        return dataSource
    }

}