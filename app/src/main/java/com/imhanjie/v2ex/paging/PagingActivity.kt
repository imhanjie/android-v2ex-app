package com.imhanjie.v2ex.paging

import android.os.Bundle
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.databinding.ActivityPagingBinding

class PagingActivity : BaseActivity<ActivityPagingBinding, PagingViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adapter = StudentAdapter()
        view.recyclerView.layoutManager = LinearLayoutManager(this)
        view.recyclerView.adapter = adapter
        vm.allStudents.observe(this) {
            adapter.submitList(it)
        }
    }

}