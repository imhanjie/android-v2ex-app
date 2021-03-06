package com.imhanjie.v2ex.view

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.imhanjie.support.ext.dpi
import com.imhanjie.support.ext.postDelayed
import com.imhanjie.support.ext.toast
import com.imhanjie.v2ex.BaseActivity
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.ActivityLoginBinding
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.v2ex.vm.LoginViewModel
import com.imhanjie.widget.LoadingWrapLayout

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val vm: LoginViewModel by viewModels()

    override fun getViewModels(): List<BaseViewModel> {
        return listOf(vm)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 验证码图片流
        vm.imageInputStream.observe(this) { resp ->
            Glide.with(this)
                .load(BitmapFactory.decodeStream(resp))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(RoundedCorners(3.dpi))
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
        vm.loadSignInState.observe(this) { ing ->
            vb.loadingLayout.update(if (ing) LoadingWrapLayout.Status.LOADING else LoadingWrapLayout.Status.DONE)
        }
        // 登录中
        vm.loginState.observe(this) { ing ->
            loadingDialog.update(!ing)
            if (!ing) {
                vb.etVer.text = null
            }
        }
        // 登录结果
        vm.loginResult.observe(this) {
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