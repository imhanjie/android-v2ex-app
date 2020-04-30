package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.model.LoginResult
import com.imhanjie.v2ex.parser.model.SignIn
import com.imhanjie.v2ex.repository.provideAppRepository
import okhttp3.ResponseBody

class LoginViewModel(application: Application) : BaseViewModel(application) {

    val imageResponseBody = MutableLiveData<ResponseBody>()
    val loginResult = MutableLiveData<LoginResult>()

    private lateinit var signInData: SignIn

    fun loadSignIn() {
        request {
            signInData = provideAppRepository().loadSignIn()
            imageResponseBody.value = provideAppRepository().loadImage(signInData.verificationUrl)
        }
    }

    fun login(userName: String, password: String, verCode: String) {
        request {
            loginResult.value = provideAppRepository().login(signInData, userName, password, verCode)
        }
    }

}