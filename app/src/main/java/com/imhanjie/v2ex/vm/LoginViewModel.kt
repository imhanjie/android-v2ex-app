package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.AppSession
import com.imhanjie.v2ex.model.LoginInfo
import com.imhanjie.v2ex.parser.model.SignIn
import com.imhanjie.v2ex.repository.provideAppRepository
import java.io.InputStream

class LoginViewModel(application: Application) : BaseViewModel(application) {

    private val _imageInputStream = MutableLiveData<InputStream>()

    val imageInputStream: LiveData<InputStream>
        get() = _imageInputStream

    private val _loginResult = MutableLiveData<LoginInfo>()

    val loginResult: LiveData<LoginInfo>
        get() = _loginResult

    private val _loginState = MutableLiveData<Boolean>()

    val loginState: LiveData<Boolean>
        get() = _loginState

    private val _loadSignInState = MutableLiveData<Boolean>()

    val loadSignInState: LiveData<Boolean>
        get() = _loadSignInState

    private var signInData: SignIn? = null

    /**
     * 获取用于登陆的信息
     */
    fun loadSignIn() {
        request(
            onRequest = {
                _loadSignInState.value = true
                signInData = provideAppRepository().loadSignIn()
                _imageInputStream.value = provideAppRepository().loadVerImage(signInData!!.verUrlOnce)
            },
            onError = {
                _loadSignInState.value = false
            }
        )
    }

    /**
     * 登录
     */
    fun login(userName: String, password: String, verCode: String) {
        if (userName.isEmpty() || password.isEmpty() || verCode.isEmpty() || signInData == null) {
            return
        }
        request(
            onRequest = {
                _loginState.value = true
                val loginInfo = provideAppRepository().login(signInData!!, userName, password, verCode)
                AppSession.setOrUpdateUserInfo(AppSession.getUserInfo().copy(a2Cookie = loginInfo.cookie))
                _loginResult.value = loginInfo
            },
            onError = {
                loadSignIn()
            },
            onComplete = {
                _loginState.value = false
            }
        )
    }

}