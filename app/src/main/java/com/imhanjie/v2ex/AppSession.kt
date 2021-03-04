package com.imhanjie.v2ex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.support.PreferencesManager
import com.imhanjie.v2ex.api.model.MyUserInfo
import com.imhanjie.v2ex.common.SpConstants
import com.imhanjie.v2ex.model.LocalUserInfo

object AppSession {

    private val userInfoLiveData = MutableLiveData(
        PreferencesManager.getInstance(SpConstants.FILE_APP_SESSION).run {
            LocalUserInfo(
                getString(SpConstants.USER_NAME, ""),
                getString(SpConstants.USER_AVATAR, ""),
                getString(SpConstants.A2_COOKIE, ""),
                getLong(SpConstants.MONEY_GOLD, 0),
                getLong(SpConstants.MONEY_SILVER, 0),
                getLong(SpConstants.MONEY_BRONZE, 0)
            )
        }
    )

    fun getUserInfo() = userInfoLiveData as LiveData<LocalUserInfo>

    private val loginStateLiveData = MutableLiveData<Boolean>(isLogin())

    fun getLoginState() = loginStateLiveData as LiveData<Boolean>

    fun isLogin() = getUserInfo().value!!.a2Cookie.isNotEmpty()

    fun setOrUpdateUserInfo(info: MyUserInfo) {
        setOrUpdateUserInfo(
            this.userInfoLiveData.value!!.copy(
                userName = info.name,
                userAvatar = info.avatar,
                moneyGold = info.moneyGold,
                moneySilver = info.moneySilver,
                moneyBronze = info.moneyBronze
            )
        )
    }

    fun setOrUpdateUserInfo(userInfo: LocalUserInfo) {
        val preIsLogin = isLogin()
        this.userInfoLiveData.value = userInfo
        PreferencesManager.getInstance(SpConstants.FILE_APP_SESSION).apply {
            putString(SpConstants.USER_NAME, userInfo.userName)
            putString(SpConstants.USER_AVATAR, userInfo.userAvatar)
            putString(SpConstants.A2_COOKIE, userInfo.a2Cookie)
            putLong(SpConstants.MONEY_GOLD, userInfo.moneyGold)
            putLong(SpConstants.MONEY_SILVER, userInfo.moneySilver)
            putLong(SpConstants.MONEY_BRONZE, userInfo.moneyBronze)
        }

        // 状态发生变更时才进行分发登录状态
        val currentIsLogin = isLogin()
        if (currentIsLogin != preIsLogin) {
            loginStateLiveData.value = currentIsLogin
        }
    }

    fun clear() {
        val preIsLogin = isLogin()
        this.userInfoLiveData.value = LocalUserInfo.EMPTY
        PreferencesManager.getInstance(SpConstants.FILE_APP_SESSION).clearAll()
        // 状态发生变更时才进行分发登录状态
        if (preIsLogin) {
            loginStateLiveData.value = false
        }
    }

}