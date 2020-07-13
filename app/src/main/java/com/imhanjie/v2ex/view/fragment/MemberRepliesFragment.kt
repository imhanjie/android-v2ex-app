package com.imhanjie.v2ex.view.fragment

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.drakeet.multitype.MultiTypeAdapter
import com.imhanjie.v2ex.api.model.MemberReplies
import com.imhanjie.v2ex.common.ExtraKeys
import com.imhanjie.v2ex.common.MissingArgumentException
import com.imhanjie.v2ex.common.ViewModelProvider
import com.imhanjie.v2ex.common.valueIsNull
import com.imhanjie.v2ex.view.BasePageFragment
import com.imhanjie.v2ex.view.adapter.MemberReplyAdapter
import com.imhanjie.v2ex.view.adapter.diff.MemberReplyDiffCallback
import com.imhanjie.v2ex.vm.MemberRepliesViewModel

class MemberRepliesFragment : BasePageFragment<MemberRepliesViewModel>() {

    override fun autoLoadDataList(): Boolean {
        return false
    }

    override fun getViewModel(): MemberRepliesViewModel {
        val userName = arguments?.getString(ExtraKeys.USER_NAME) ?: throw MissingArgumentException(ExtraKeys.USER_NAME)
        return ViewModelProvider(this) { MemberRepliesViewModel(userName, requireActivity().application) }
    }

    override fun getDiffCallback(oldItems: List<Any>, newItems: List<Any>): DiffUtil.Callback {
        return MemberReplyDiffCallback(oldItems, newItems)
    }

    override fun registerAdapter(adapter: MultiTypeAdapter) {
        val replyAdapter = MemberReplyAdapter()
        adapter.register(MemberReplies.Item::class.java, replyAdapter)
    }

    override fun onResume() {
        super.onResume()
        if (vm.pageData.valueIsNull()) {
            vm.loadDataList(loadMore = false)
        }
    }

    companion object {
        fun newInstance(userName: String): MemberRepliesFragment {
            val fragment = MemberRepliesFragment()
            fragment.arguments = Bundle().apply {
                putString(ExtraKeys.USER_NAME, userName)
            }
            return fragment
        }
    }

}