package com.imhanjie.v2ex.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.observe
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.common.ExtraKeys
import com.imhanjie.v2ex.databinding.ActivityPreviewTopicBinding
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.PreviewTopicViewModel

class PreviewTopicActivity : BaseActivity<ActivityPreviewTopicBinding>() {

    private val vm: PreviewTopicViewModel by viewModels()

    override fun getViewModels(): List<BaseViewModel> {
        return listOf(vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val content = intent.getStringExtra(ExtraKeys.CONTENT) ?: ""
        vm.richContent.observe(this) {
            vb.tvContent.setRichContent(it)
        }
        vm.previewContent(content)
    }

    companion object {
        fun start(from: Any, content: String) {
            if (content.isEmpty()) {
                return
            }
            from.toActivity<PreviewTopicActivity>(
                bundleOf(
                    ExtraKeys.CONTENT to content
                )
            )
        }
    }

}