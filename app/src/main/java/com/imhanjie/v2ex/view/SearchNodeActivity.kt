package com.imhanjie.v2ex.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.activity.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.imhanjie.support.ext.postDelayed
import com.imhanjie.support.ext.toActivity
import com.imhanjie.support.showKeyBoard
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.api.model.SearchNode
import com.imhanjie.v2ex.common.ExtraKeys
import com.imhanjie.v2ex.databinding.ActivitySearchNodeBinding
import com.imhanjie.v2ex.databinding.ItemSearchNodeBinding
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.SearchNodeViewModel
import com.imhanjie.widget.listener.BaseTextWatcher
import com.imhanjie.widget.recyclerview.base.BaseAdapter

class SearchNodeActivity : BaseActivity<ActivitySearchNodeBinding>() {

    private val vm: SearchNodeViewModel by viewModels()

    private val nodes = mutableListOf<SearchNode>()

    override fun getViewModels(): List<BaseViewModel> {
        return listOf(vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        postDelayed(delayMillis = 150) { showKeyBoard(this, vb.etSearch) }
        vb.etSearch.addTextChangedListener(object : BaseTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                val keyWords = s.toString().trim()
                vm.searchNodes(keyWords)
            }
        })

        vb.rvNodes.layoutManager = LinearLayoutManager(this)
        val adapter = object : BaseAdapter<ItemSearchNodeBinding, SearchNode>(nodes) {
            override fun bindTo(vb: ItemSearchNodeBinding, position: Int, item: SearchNode) {
                vb.tvNode.text = item.text
            }
        }
        adapter.onItemClickListener = { _, _, position ->
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(ExtraKeys.NODE, nodes[position])
            })
            finish()
        }
        vb.rvNodes.adapter = adapter

        vm.searchNodes.observe(this) {
            nodes.clear()
            nodes.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        fun start(from: Any, requestCode: Int) {
            from.toActivity<SearchNodeActivity>(requestCode = requestCode)
        }

        fun extractResult(data: Intent): SearchNode {
            return data.getParcelableExtra(ExtraKeys.NODE) as SearchNode
        }
    }

}