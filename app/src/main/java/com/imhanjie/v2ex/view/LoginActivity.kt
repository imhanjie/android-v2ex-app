package com.imhanjie.v2ex.view

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.imhanjie.support.ext.dp
import com.imhanjie.support.ext.postDelayed
import com.imhanjie.support.ext.toast
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.ActivityLoginBinding
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.LoginViewModel
import com.imhanjie.widget.LoadingWrapLayout

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private lateinit var vm: LoginViewModel

    override fun initViewModels(): List<BaseViewModel> {
        vm = ViewModelProvider(this).get(LoginViewModel::class.java)
        return listOf(vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 验证码图片流
        vm.imageInputStreamLiveData.observe(this) { resp ->
            Glide.with(this)
                .load(BitmapFactory.decodeStream(resp))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(RoundedCorners(3f.dp().toInt()))
                .into(object : CustomViewTarget<ImageView, Drawable>(vb.ivVerification) {
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        vb.loadingLayout.update(LoadingWrapLayout.Status.FAIL)
                    }

                    override fun onResourceCleared(placeholder: Drawable?) {
                    }

                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        vb.ivVerification.setImageDrawable(resource)
                        vb.loadingLayout.update(LoadingWrapLayout.Status.DONE)
                    }
                })
        }
        // 验证码 & 登录信息
        vm.loadSignInStateLiveData.observe(this) { ing ->
            vb.loadingLayout.update(if (ing) LoadingWrapLayout.Status.LOADING else LoadingWrapLayout.Status.DONE)
        }
        // 登录中
        vm.loginStateLiveData.observe(this) { ing ->
            loadingDialog.update(!ing)
            if (!ing) {
                vb.etVer.text = null
            }
        }
        // 登录结果
        vm.loginResultLiveData.observe(this) {
            postDelayed(150) {
                toast(R.string.tips_login_success)
                finish()
            }
        }

        vb.ivVerification.setOnClickListener { vm.loadSignIn() }

        vb.btnLogin.setOnClickListener {
            vm.login(
                vb.etUserName.text.toString().trim(),
                vb.etPassword.text.toString().trim(),
                vb.etVer.text.toString().trim()
            )
        }

        vb.loadingLayout.update(LoadingWrapLayout.Status.LOADING)
        vb.loadingLayout.retryCallback = {
            vm.loadSignIn()
        }

        vm.loadSignIn()
    }

}