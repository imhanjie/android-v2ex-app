package com.imhanjie.v2ex.view.fragment

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
import com.imhanjie.v2ex.view.NodeActivity
import com.imhanjie.v2ex.view.adapter.TopicAdapter
import com.imhanjie.v2ex.vm.TabViewModel
import com.imhanjie.widget.LineDividerItemDecoration
import com.imhanjie.widget.LoadingWrapLayout

class TabFragment : BaseFragment<FragmentTabBinding>() {

    private lateinit var vm: TabViewModel

    override fun getViewModels() = listOf(vm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tab = arguments?.getSerializable("tab") as? TopicTab
            ?: throw IllegalArgumentException("缺少 tab 参数")
        /**
         * 这里为了使 ViewModel 存活时间更久以持有列表数据，以 tab 的 value 字段作为 key 存储。
         * 这样可以保证在 Activity 的生命周期内，每个 Tab Fragment 的不同实例都能一直对应一个 ViewModel，
         * 即可忽略 ViewPager 的 Fragment 回收导致的数据重新加载问题。
         */
        vm = ViewModelProvider(requireActivity()).get(tab.value, TabViewModel::class.java)
        if (vm.topicLiveData.value == null) {   // 首次初始化
            vm.tab = tab
        }
    }

    override fun initViews() {
        vb.loadingLayout.update(LoadingWrapLayout.Status.LOADING)
        vm.swipeStateLiveData.observe(this) { vb.swipeRefreshLayout.isRefreshing = it }

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
//            toActivity<TopicActivity>(mapOf("topicId" to item.id))
            toActivity<NodeActivity>(
                mapOf(
                    "title" to item.nodeTitle,
                    "name" to item.nodeName
                )
            )
        }
        vb.topicRv.adapter = adapter
        vm.topicLiveData.observe(this) {
            vb.loadingLayout.update(LoadingWrapLayout.Status.DONE)
            adapter.submitList(it)
        }

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

    override fun onResume() {
        super.onResume()
        if (vm.topicLiveData.value == null) {   // 首次初始化
            vm.loadTopics(false)
        }
    }

}