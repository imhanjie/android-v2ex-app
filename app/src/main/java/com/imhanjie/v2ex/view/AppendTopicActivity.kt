package com.imhanjie.v2ex.view

import android.os.Bundle
import androidx.core.os.bundleOf
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.common.ExtraKeys
import com.imhanjie.v2ex.common.MissingArgumentException
import com.imhanjie.v2ex.common.ViewModelProvider
import com.imhanjie.v2ex.databinding.ActivityAppendTopicBinding
import com.imhanjie.v2ex.vm.AppendTopicViewModel
import com.imhanjie.v2ex.vm.BaseViewModel

class AppendTopicActivity : BaseActivity<ActivityAppendTopicBinding>() {

    private lateinit var vm: AppendTopicViewModel

    override fun initViewModels(): List<BaseViewModel> {
        val topicId = intent.getLongExtra(ExtraKeys.TOPIC_ID, -1L)
        if (topicId < 0) {
            throw MissingArgumentException(ExtraKeys.TOPIC_ID)
        }
        vm = ViewModelProvider(this) { AppendTopicViewModel(topicId, application) }
        return listOf(vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        vb.tvPreview.setOnClickListener {
            PreviewTopicActivity.start(this, vb.etContent.text.toString().trim())
        }
    }

    companion object {
        fun start(from: Any, topicId: Long) {
            from.toActivity<AppendTopicActivity>(
                bundleOf(
                    ExtraKeys.TOPIC_ID to topicId
                )
            )
        }
    }

}