package com.imhanjie.v2ex.view.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.drakeet.multitype.MultiTypeAdapter
import com.imhanjie.support.ext.dp
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.BaseFragment
import com.imhanjie.v2ex.common.Event
import com.imhanjie.v2ex.common.LiveDataBus
import com.imhanjie.v2ex.common.TopicTab
import com.imhanjie.v2ex.databinding.FragmentTabBinding
import com.imhanjie.v2ex.parser.model.TopicItem
import com.imhanjie.v2ex.view.NodeActivity
import com.imhanjie.v2ex.view.TopicActivity
import com.imhanjie.v2ex.view.adapter.TopicAdapter
import com.imhanjie.v2ex.vm.TabViewModel
import com.imhanjie.widget.LineDividerItemDecoration
import com.imhanjie.widget.LoadingWrapLayout

class TabFragment : BaseFragment<FragmentTabBinding>() {

    private lateinit var vm: TabViewModel
    private val items = arrayListOf<Any>()

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

        vb.topicRv.layoutManager = LinearLayoutManager(context)
        (vb.topicRv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        vb.topicRv.addItemDecoration(
            LineDividerItemDecoration(
                requireContext(),
                height = 4f.dp().toInt()
            )
        )

        val adapter = MultiTypeAdapter(items)
        val topicAdapter = TopicAdapter().apply {
            onItemClickListener = { _, item, _ ->
                this@TabFragment.toActivity<TopicActivity>(mapOf("topicId" to item.id))
                this@TabFragment.toActivity<NodeActivity>(
                    mapOf(
                        "title" to item.nodeTitle,
                        "name" to item.nodeName
                    )
                )
            }
        }
        adapter.apply {
            register(TopicItem::class.java, topicAdapter)
        }
        vb.topicRv.adapter = adapter
        vm.topicLiveData.observe(this) {
            vb.loadingLayout.update(LoadingWrapLayout.Status.DONE)
            vb.swipeRefreshLayout.isRefreshing = false

            items.clear()
            items.addAll(it)
            adapter.notifyDataSetChanged()
        }

        vb.swipeRefreshLayout.setOnRefreshListener { vm.loadTopics() }

        LiveDataBus.get()
            .with(Event.MAIN_SCROLL_TO_TOP, Any::class.java)
            .observe(this) {
                if (isResumed) {
                    vb.topicRv.smoothScrollToPosition(0)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        if (vm.topicLiveData.value == null) {   // 首次初始化
            vm.loadTopics()
        }
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