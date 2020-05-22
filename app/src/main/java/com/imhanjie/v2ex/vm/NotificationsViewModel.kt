package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.parser.model.Notifications
import com.imhanjie.v2ex.repository.provideAppRepository

class NotificationsViewModel(application: Application) : BaseViewModel(application) {

    data class NotificationLiveData(
        val notifications: Notifications,
        val append: Boolean,
        val hasMore: Boolean
    )

    private val _notification = MutableLiveData<NotificationLiveData>()

    val notification: LiveData<NotificationLiveData>
        get() = _notification

    private var currentPage = 1

    fun loadNotifications(append: Boolean) {
        request {
            val requestPage = if (!append) 1 else currentPage + 1
            val notifications = provideAppRepository().loadNotifications(requestPage)
            _notification.value = NotificationLiveData(
                notifications,
                append,
                notifications.currentPage != notifications.totalPage
            )
            currentPage = requestPage
        }
    }

}