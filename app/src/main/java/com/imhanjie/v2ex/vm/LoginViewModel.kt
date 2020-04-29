package com.imhanjie.v2ex.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.imhanjie.v2ex.model.LoginResult
import com.imhanjie.v2ex.parser.model.SignIn
import com.imhanjie.v2ex.repository.provideAppRepository
import okhttp3.ResponseBody

class LoginViewModel(application: Application) : BaseViewModel(application) {

    val signInData = MutableLiveData<SignIn>()
    val imageData = MutableLiveData<ResponseBody>()

    val loginResult = MutableLiveData<LoginResult>()

    fun loadSignIn() {
        request {
            val result = provideAppRepository().loadSignIn()
            signInData.value = result
            imageData.value = provideAppRepository().loadImage(result.verificationUrl)
        }
    }

    fun login(userName: String, password: String, verCode: String) {
        request {
            loginResult.value = provideAppRepository().login(signInData.value!!, userName, password, verCode)
        }
    }

}