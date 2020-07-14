package com.imhanjie.v2ex.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.imhanjie.support.ext.postDelayed
import com.imhanjie.support.ext.toast
import com.imhanjie.support.showKeyBoard
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.ActivityCreateTopicBinding
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.CreateTopicViewModel

class CreateTopicActivity : BaseActivity<ActivityCreateTopicBinding>() {

    companion object {
        const val REQUEST_NODE = 1000
    }

    private val vm: CreateTopicViewModel by viewModels()

    override fun initViewModels(): List<BaseViewModel> = listOf(vm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        postDelayed(delayMillis = 150) { showKeyBoard(this, vb.etTitle) }
        vb.tvNode.setOnClickListener {
            SearchNodeActivity.start(this, REQUEST_NODE)
        }
        vb.tvPreview.setOnClickListener {
            PreviewTopicActivity.start(this, vb.etContent.text.toString().trim())
        }
        vb.topBar.setOnRightClickListener(View.OnClickListener {
            vm.createTopic(vb.etTitle.text.toString().trim(), vb.etContent.text.toString().trim())
        })

        vm.selectedNode.observe(this) {
            vb.tvNode.text = it.text
        }
        vm.newTopicId.observe(this) { newTopicId ->
            TopicActivity.start(this, newTopicId)
            toast(R.string.tips_success_create_topic)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK || data == null) {
            return
        }
        when (requestCode) {
            REQUEST_NODE -> {
                val selectedNode = SearchNodeActivity.extractResult(data)
                vm.setSelectedNode(selectedNode)
            }
        }
    }

}