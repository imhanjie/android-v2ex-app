package com.imhanjie.v2ex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.support.PreferencesManager
import com.imhanjie.v2ex.common.LocalUserInfo
import com.imhanjie.v2ex.common.SpConstants

object AppSession {

    /**
     * 登录状态
     */
    private val loginStateLiveData = MutableLiveData<Boolean>()
    fun getLoginStateLiveData() = loginStateLiveData as LiveData<Boolean>

    private var userInfo: LocalUserInfo = LocalUserInfo.EMPTY

    fun getUserInfo(): LocalUserInfo {
        return if (this.userInfo != LocalUserInfo.EMPTY) {
            this.userInfo
        } else {
            PreferencesManager.getInstance(SpConstants.FILE_APP_SESSION).run {
                LocalUserInfo(
                    getString(SpConstants.USER_NAME, ""),
                    getString(SpConstants.USER_AVATAR, ""),
                    getString(SpConstants.A2_COOKIE, "")
                )
            }
        }
    }

    fun setOrUpdateUserInfo(userInfo: LocalUserInfo) {
        val preIsLogin = isLogin()

        this.userInfo = userInfo

        PreferencesManager.getInstance(SpConstants.FILE_APP_SESSION).apply {
            putString(SpConstants.USER_NAME, userInfo.userName)
            putString(SpConstants.USER_AVATAR, userInfo.userAvatar)
            putString(SpConstants.A2_COOKIE, userInfo.a2Cookie)
        }

        // 状态发生变更时才进行分发登录状态
        val currentIsLogin = isLogin()
        if (currentIsLogin != preIsLogin) {
            loginStateLiveData.value = currentIsLogin
        }
    }

    fun isLogin() = getUserInfo().a2Cookie.isNotEmpty()

    fun clear() {
        val preIsLogin = isLogin()
        this.userInfo = LocalUserInfo.EMPTY
        PreferencesManager.getInstance(SpConstants.FILE_APP_SESSION).clearAll()
        // 状态发生变更时才进行分发登录状态
        if (preIsLogin) {
            loginStateLiveData.value = false
        }
    }

}