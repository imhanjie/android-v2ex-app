package com.imhanjie.v2ex.view

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.imhanjie.support.ext.dp
import com.imhanjie.support.ext.getResColor
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.ActivityMainBinding
import com.imhanjie.v2ex.vm.MyViewModel
import com.imhanjie.widget.LineDividerItemDecoration

class MainActivity : BaseActivity<ActivityMainBinding, MyViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.loadingState.observe(this) { loading ->
            if (loading) view.loadingLayout.show() else view.loadingLayout.hide()
        }

        view.topicRv.layoutManager = LinearLayoutManager(this)
        view.topicRv.addItemDecoration(
            LineDividerItemDecoration(
                this,
                color = getResColor(R.color.topic_list_divider),
                height = 4f.dp().toInt()
            )
        )
        val adapter = TopicAdapter()
        view.topicRv.adapter = adapter

        vm.topicData.observe(this) { adapter.submitList(it) }

        vm.topicData.observe(this) { topics ->
            for (topic in topics) {
                Log.e("bingo", topic.toString())
            }
        }
    }

}
