package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.imhanjie.support.PreferencesManager
import com.imhanjie.v2ex.common.SpConstants
import com.imhanjie.v2ex.model.LoginInfo
import com.imhanjie.v2ex.parser.model.SignIn
import com.imhanjie.v2ex.repository.provideAppRepository
import java.io.InputStream

class LoginViewModel(application: Application) : BaseViewModel(application) {

    val imageInputStream = MutableLiveData<InputStream>()
    val loginResult = MutableLiveData<LoginInfo>()
    val loginState = MutableLiveData<Boolean>()
    val loadSignInState = MutableLiveData<Boolean>()

    private var signInData: SignIn? = null

    /**
     * 获取用于登陆的信息
     */
    fun loadSignIn() {
        request(
            onRequest = {
                loadSignInState.value = true
                signInData = provideAppRepository().loadSignIn()
                imageInputStream.value = provideAppRepository().loadImage(signInData!!.verificationUrl)
            },
            onError = {
                loadSignInState.value = false
            }
        )
    }

    /**
     * 登陆
     */
    fun login(userName: String, password: String, verCode: String) {
        if (userName.isEmpty() || password.isEmpty() || verCode.isEmpty() || signInData == null) {
            return
        }
        request(
            onRequest = {
                loginState.value = true
                val loginInfo = provideAppRepository().login(signInData!!, userName, password, verCode)
                PreferencesManager.getInstance(SpConstants.FILE_COOKIES).putString(SpConstants.COOKIE_A2, loginInfo.cookie)
                loginResult.value = loginInfo
            },
            onError = {
                loadSignIn()
            },
            onComplete = {
                loginState.value = false
            }
        )
    }

}