package com.imhanjie.v2ex.view

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.imhanjie.support.e
import com.imhanjie.support.ext.dp
import com.imhanjie.support.ext.toast
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.databinding.ActivityLoginBinding
import com.imhanjie.v2ex.vm.LoginViewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private lateinit var vm: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(this).get(LoginViewModel::class.java)
        vm.error.observe(this) { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }

        vm.signInData.observe(this) {
//            vb.tvKeyUserName.text = "userName: ${it.keyUserName}"
//            vb.tvKeyPassword.text = "userName: ${it.keyPassword}"
//            vb.tvKeyOnce.text = "userName: ${it.keyOnce}"
            e("url: ${it.verificationUrl}")
//            Glide.with(this)
//                .load(it.verificationUrl)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(vb.ivVerification)
        }

        vm.imageData.observe(this) { resp ->
            resp.let {
                Glide.with(this)
                    .load(BitmapFactory.decodeStream(it.byteStream()))
                    .transform(RoundedCorners(3f.dp().toInt()))
                    .into(vb.ivVerification)
            }
        }

        vm.loginResult.observe(this) { result ->
            if (result.success) {
                toast("登录成功")
            } else {
                toast(result.errorMsg)
            }
        }

        vb.btnLogin.setOnClickListener {
            vm.login(vb.etUserName.text.toString().trim(), vb.etPassword.text.toString().trim(), vb.etVer.text.toString().trim())
        }

        vm.loadSignIn()
    }

}