package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.model.LoginInfo
import com.imhanjie.v2ex.parser.model.SignIn
import com.imhanjie.v2ex.repository.provideAppRepository
import java.io.InputStream

class LoginViewModel(application: Application) : BaseViewModel(application) {

    val imageInputStream = MutableLiveData<InputStream>()
    val loginResult = MutableLiveData<LoginInfo>()

    private lateinit var signInData: SignIn

    fun loadSignIn() {
        request {
            signInData = provideAppRepository().loadSignIn()
            imageInputStream.value = provideAppRepository().loadImage(signInData.verificationUrl)
        }
    }

    fun login(userName: String, password: String, verCode: String) {
        request {
            loginResult.value = provideAppRepository().login(signInData, userName, password, verCode)
        }
    }

}