package com.imhanjie.v2ex.paging

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.imhanjie.v2ex.App
import com.imhanjie.v2ex.vm.BaseViewModel

class PagingViewModel : BaseViewModel() {

    private val dao = AppDatabase.get(App.INSTANCE).studentDao()

    val allStudents: LiveData<PagedList<Student>> = LivePagedListBuilder(
        StudentDataSourceFactory(), PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .setPrefetchDistance(1)
            .setInitialLoadSizeHint(30)
            .build()
    ).build()

//    fun insertMockData() {
//        request {
//            insertData()
//        }
//    }
//
//    private suspend fun insertData() = withContext(Dispatchers.IO) {
//        AppDatabase.get(App.INSTANCE).studentDao().insert(
//            AppDatabase.CHEESE_DATA.map { Student(id = 0, name = it) }
//        )
//    }

}