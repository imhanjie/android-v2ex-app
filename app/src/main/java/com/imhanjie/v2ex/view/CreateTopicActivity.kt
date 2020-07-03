package com.imhanjie.v2ex.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.imhanjie.support.ext.postDelayed
import com.imhanjie.support.ext.toast
import com.imhanjie.support.showKeyBoard
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.databinding.ActivityCreateTopicBinding
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.CreateTopicViewModel

class CreateTopicActivity : BaseActivity<ActivityCreateTopicBinding>() {

    companion object {
        const val REQUEST_NODE = 1000
    }

    private lateinit var vm: CreateTopicViewModel

    override fun initViewModels(): List<BaseViewModel> {
        vm = ViewModelProvider(this).get(CreateTopicViewModel::class.java)
        return listOf(vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        SearchNodeActivity.start(this, REQUEST_NODE)
        initViews()
    }

    private fun initViews() {
        postDelayed(delayMillis = 150) { showKeyBoard(this, vb.etTitle) }
        vb.topBar.setOnRightClickListener(View.OnClickListener {
            PreviewTopicActivity.start(
                this,
                vb.etTitle.text.toString().trim(),
                vb.etContent.text.toString().trim(),
                ""
            )
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK || data == null) {
            return
        }
        when (requestCode) {
            REQUEST_NODE -> {
                val selectedNode = SearchNodeActivity.extractResult(data)
                toast(selectedNode.text)
            }
        }
    }

}