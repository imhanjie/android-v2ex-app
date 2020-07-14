package com.imhanjie.v2ex.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.observe
import com.imhanjie.support.ext.postDelayed
import com.imhanjie.support.ext.toActivity
import com.imhanjie.support.showKeyBoard
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
        postDelayed(delayMillis = 150) { showKeyBoard(this, vb.etContent) }
        vb.tvPreview.setOnClickListener {
            PreviewTopicActivity.start(this, vb.etContent.text.toString().trim())
        }
        vb.topBar.setOnRightClickListener(View.OnClickListener {
            vm.appendTopic(vb.etContent.text.toString().trim())
        })

        vm.appendResult.observe(this) {
            if (!it) {
                return@observe
            }
            finish()
            globalViewModel.appendTopic.value = Any()
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