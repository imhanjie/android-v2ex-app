package com.imhanjie.v2ex.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imhanjie.support.ext.dp
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.BaseFragment
import com.imhanjie.v2ex.common.TopicTab
import com.imhanjie.v2ex.databinding.FragmentTabBinding
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.TabViewModel
import com.imhanjie.widget.LineDividerItemDecoration

class TabFragment : BaseFragment<FragmentTabBinding>() {

    private lateinit var vm: TabViewModel

    override fun getViewModels(): List<BaseViewModel> = listOf(vm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tab = arguments?.getSerializable("tab") as? TopicTab
            ?: throw IllegalArgumentException("缺少 tab 参数")
        /**
         * 这里为了使 ViewModel 存活时间更久以持有列表数据，并以 tab 的 value 字段作为 key 存储。
         * 这样可以保证在 Activity 的生命周期内，每个 Tab 的不同实例都能一直对应一个 ViewModel，
         * 即可忽略 ViewPager 的 Fragment 回收导致的数据重新加载问题。
         */
        vm = ViewModelProvider(requireActivity()).get(tab.value, TabViewModel::class.java)
        if (vm.topicData.value == null) {   // 首次初始化
            vm.tab = tab
            vm.loadTopics(false)
        }
    }

    override fun initViews() {
        vm.loadingState.observe(this) { vb.loadingLayout.update(!it) }
        vm.swipeLoadingState.observe(this) { vb.swipeRefreshLayout.isRefreshing = it }

        vb.topicRv.layoutManager = LinearLayoutManager(context)
        (vb.topicRv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        vb.topicRv.addItemDecoration(
            LineDividerItemDecoration(
                requireContext(),
                height = 4f.dp().toInt()
            )
        )
        val adapter = TopicAdapter()
        adapter.onItemClickListener = { _, item, _ ->
            toActivity<TopicActivity>(mapOf("topicId" to item.id))
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