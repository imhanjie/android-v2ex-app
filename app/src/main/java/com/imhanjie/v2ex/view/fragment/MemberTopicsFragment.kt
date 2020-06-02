package com.imhanjie.v2ex.view.fragment

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import com.drakeet.multitype.MultiTypeAdapter
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.api.model.TopicItem
import com.imhanjie.v2ex.common.MissingArgumentException
import com.imhanjie.v2ex.common.ViewModelProvider
import com.imhanjie.v2ex.common.valueIsNull
import com.imhanjie.v2ex.view.BasePageFragment
import com.imhanjie.v2ex.view.TopicActivity
import com.imhanjie.v2ex.view.adapter.MemberTopicAdapter
import com.imhanjie.v2ex.view.adapter.diff.TopicDiffCallback
import com.imhanjie.v2ex.vm.MemberTopicsViewModel

class MemberTopicsFragment : BasePageFragment<MemberTopicsViewModel>() {

    override fun autoLoadDataList(): Boolean {
        return false
    }

    override fun getViewModel(): MemberTopicsViewModel {
        val userName = arguments?.getString("userName")
            ?: throw MissingArgumentException("userName")
        return ViewModelProvider(this) { MemberTopicsViewModel(userName, requireActivity().application) }
    }

    override fun getDiffCallback(oldItems: List<Any>, newItems: List<Any>): DiffUtil.Callback {
        return TopicDiffCallback(oldItems, newItems)
    }

    override fun registerAdapter(adapter: MultiTypeAdapter) {
        val topicAdapter = MemberTopicAdapter().apply {
            onItemClickListener = { _, item, _ ->
                this@MemberTopicsFragment.toActivity<TopicActivity>(
                    bundleOf(
                        "topicId" to item.id
                    )
                )
            }
        }
        adapter.register(TopicItem::class.java, topicAdapter)
    }

    override fun onResume() {
        super.onResume()
        if (vm.pageData.valueIsNull()) {
            vm.loadDataList(loadMore = false)
        }
    }

    companion object {
        fun newInstance(userName: String): MemberTopicsFragment {
            val fragment = MemberTopicsFragment()
            fragment.arguments = Bundle().apply {
                putString("userName", userName)
            }
            return fragment
        }
    }

}