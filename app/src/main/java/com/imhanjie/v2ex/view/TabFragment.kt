package com.imhanjie.v2ex.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.imhanjie.support.ext.dp
import com.imhanjie.support.ext.getResColor
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.BaseFragment
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.FragmentTabBinding
import com.imhanjie.v2ex.model.TopicTab
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.TabViewModel
import com.imhanjie.widget.LineDividerItemDecoration

class TabFragment : BaseFragment<FragmentTabBinding>() {

    private lateinit var vm: TabViewModel

    override fun getViewModels(): List<BaseViewModel> = listOf(vm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tab = arguments?.getSerializable("tab") as? TopicTab
            ?: throw IllegalArgumentException("missing tab param")

        vm = ViewModelProvider(activity!!).get(tab.value, TabViewModel::class.java)
        vm.topicData
        if (vm.topicData.value == null) {   // 首次初始化
            vm.tab = tab
            vm.loadTopics(false)
        }
    }

    override fun initViews() {
        vm.loadingState.observe(this) { loading ->
            if (loading) {
                vb.loadingLayout.show()
            } else {
                vb.loadingLayout.hide()
            }
        }
        vm.swipeLoadingState.observe(this) { vb.swipeRefreshLayout.isRefreshing = it }

        vb.topicRv.layoutManager = LinearLayoutManager(context)
        vb.topicRv.addItemDecoration(
            LineDividerItemDecoration(
                context!!,
                color = getResColor(R.color.topic_list_divider),
                height = 4f.dp().toInt()
            )
        )
        val adapter = TopicAdapter {
            toActivity<TopicActivity>(mapOf("topicId" to it.id))
        }
        vb.topicRv.adapter = adapter
        vm.topicData.observe(this) { adapter.submitList(it) }

        vb.swipeRefreshLayout.setOnRefreshListener { vm.loadTopics(true) }
    }

    companion object {
        fun newInstance(tab: TopicTab): TabFragment {
            val fragment = TabFragment()
            fragment.arguments = Bundle().apply {
                putSerializable("tab", tab)
            }
            return fragment
        }
    }

}