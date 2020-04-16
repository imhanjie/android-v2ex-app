package com.imhanjie.v2ex.view

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.observe
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.databinding.ActivityMainBinding
import com.imhanjie.v2ex.vm.MyViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MyViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.topicData.observe(this) { topics ->
            for (topic in topics) {
                Log.e("bingo", topic.toString())
            }
        }
    }

}
