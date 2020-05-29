package com.imhanjie.v2ex.view.fragment

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import com.drakeet.multitype.MultiTypeAdapter
import com.imhanjie.v2ex.api.model.Notifications
import com.imhanjie.v2ex.view.BasePageFragment
import com.imhanjie.v2ex.view.adapter.NotificationAdapter
import com.imhanjie.v2ex.view.adapter.diff.NotificationsDiffCallback
import com.imhanjie.v2ex.vm.NotificationsViewModel

class NotificationsPageFragment : BasePageFragment<NotificationsViewModel>() {

    override fun autoLoadDataList(): Boolean {
        return false
    }

    override fun getViewModel(): NotificationsViewModel {
        return ViewModelProvider(parentFragment as NotificationsTabFragment).get(NotificationsViewModel::class.java)
    }

    override fun getDiffCallback(oldItems: List<Any>, newItems: List<Any>): DiffUtil.Callback {
        return NotificationsDiffCallback(oldItems, newItems)
    }

    override fun registerAdapter(adapter: MultiTypeAdapter) {
        val notificationAdapter = NotificationAdapter()
        adapter.register(Notifications.Item::class.java, notificationAdapter)
    }

}