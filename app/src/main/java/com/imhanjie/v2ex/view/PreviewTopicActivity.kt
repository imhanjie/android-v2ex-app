package com.imhanjie.v2ex.view

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.databinding.ActivityPreviewTopicBinding
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.PreviewTopicViewModel

class PreviewTopicActivity : BaseActivity<ActivityPreviewTopicBinding>() {

    private lateinit var vm: PreviewTopicViewModel

    override fun initViewModels(): List<BaseViewModel> {
        vm = ViewModelProvider(this).get(PreviewTopicViewModel::class.java)
        return listOf(vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = intent.getStringExtra("title") ?: ""
        val content = intent.getStringExtra("content") ?: ""
        val nodeTitle = intent.getStringExtra("nodeTitle") ?: ""
        vm.previewContent(content)
    }

    companion object {
        fun start(from: Any, title: String, content: String, nodeTitle: String) {
            if (content.isEmpty()) {
                return
            }
            from.toActivity<PreviewTopicActivity>(
                bundleOf(
                    "title" to title,
                    "content" to content,
                    "nodeTitle" to nodeTitle
                )
            )
        }
    }

}