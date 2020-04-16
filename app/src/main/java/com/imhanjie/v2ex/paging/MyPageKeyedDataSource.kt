package com.imhanjie.v2ex.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource

class MyPageKeyedDataSource :
    PageKeyedDataSource<Int, Student>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Student>
    ) {
        Log.e(
            "bingo",
            "loadInitial requestedLoadSize: " + params.requestedLoadSize + " - placeholdersEnabled: " + params.placeholdersEnabled
        )
        callback.onResult(loadStudents(0), null, 1)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Student>) {
        Log.e(
            "bingo",
            "loadAfter params: " + params.key + " - requestedLoadSize: " + params.requestedLoadSize
        )
//        callback.onResult(loadStudents(params.key), params.key + 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Student>) {
        Log.e("bingo", "loadBefore params: " + params.key)
//        callback.onResult(loadStudents(params.key), params.key - 1)
    }

    private fun loadStudents(pageIndex: Int): List<Student> {
        val result = mutableListOf<Student>()
        for (i in 1..30) {
            result.add(
                Student(i, "page: $pageIndex")
            )
        }
        return result
    }

}