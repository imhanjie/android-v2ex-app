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

    val imageInputStreamLiveData = MutableLiveData<InputStream>()
    val loginResultLiveData = MutableLiveData<LoginInfo>()
    val loginStateLiveData = MutableLiveData<Boolean>()
    val loadSignInStateLiveData = MutableLiveData<Boolean>()

    private var signInData: SignIn? = null

    /**
     * 获取用于登陆的信息
     */
    fun loadSignIn() {
        request(
            onRequest = {
                loadSignInStateLiveData.value = true
                signInData = provideAppRepository().loadSignIn()
                imageInputStreamLiveData.value = provideAppRepository().loadImage(signInData!!.verificationUrl)
            },
            onError = {
                loadSignInStateLiveData.value = false
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
                loginStateLiveData.value = true
                val loginInfo = provideAppRepository().login(signInData!!, userName, password, verCode)
                PreferencesManager.getInstance(SpConstants.FILE_COOKIES).putString(SpConstants.COOKIE_A2, loginInfo.cookie)
                loginResultLiveData.value = loginInfo
            },
            onError = {
                loadSignIn()
            },
            onComplete = {
                loginStateLiveData.value = false
            }
        )
    }

}