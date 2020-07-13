package com.imhanjie.v2ex.view.fragment

import android.os.Bundle
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import com.drakeet.multitype.MultiTypeAdapter
import com.imhanjie.v2ex.api.model.TopicItem
import com.imhanjie.v2ex.common.*
import com.imhanjie.v2ex.view.BasePageFragment
import com.imhanjie.v2ex.view.TopicActivity
import com.imhanjie.v2ex.view.adapter.TopicAdapter
import com.imhanjie.v2ex.view.adapter.diff.TopicDiffCallback
import com.imhanjie.v2ex.vm.TabViewModel

class TabFragment : BasePageFragment<TabViewModel>() {

    override fun autoLoadDataList(): Boolean {
        return false
    }

    override fun initViews() {
        super.initViews()
        globalViewModel.ignoreTopic.observe(viewLifecycleOwner) { id -> vm.removeTopic(id) }
    }

    override fun getViewModel(): TabViewModel {
        val tab = arguments?.getSerializable(ExtraKeys.TAB) as? TopicTab
            ?: throw MissingArgumentException(ExtraKeys.TAB)
        /**
         * 这里为了使 ViewModel 存活时间更久以持有列表数据，以 tab 的 value 字段作为 key 存储。
         * 这样可以保证在 Activity 的生命周期内，每个 Tab Fragment 的不同实例都能一直对应一个 ViewModel，
         * 即可忽略 ViewPager 的 Fragment 回收导致的数据重新加载问题。
         */
        return ViewModelProvider(requireActivity(), key = tab.value) {
            TabViewModel(tab, requireActivity().application)
        }
    }

    override fun getDiffCallback(oldItems: List<Any>, newItems: List<Any>): DiffUtil.Callback {
        return TopicDiffCallback(oldItems, newItems)
    }

    override fun registerAdapter(adapter: MultiTypeAdapter) {
        val topicAdapter = TopicAdapter().apply {
            onItemClickListener = { _, item, _ ->
                TopicActivity.start(this@TabFragment, item.id)
            }
        }
        adapter.register(TopicItem::class.java, topicAdapter)
    }

    override fun onResume() {
        super.onResume()
        if (vm.pageData.valueIsNull()) {   // 首次初始化
            vm.loadDataList(false)
        }
    }

    companion object {
        fun newInstance(tab: TopicTab): TabFragment {
            val fragment = TabFragment()
            fragment.arguments = Bundle().apply {
                putSerializable(ExtraKeys.TAB, tab)
            }
            return fragment
        }
    }

}