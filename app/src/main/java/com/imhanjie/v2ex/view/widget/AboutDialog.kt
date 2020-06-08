package com.imhanjie.v2ex.view.widget

import android.content.Context
import android.view.Gravity
import android.view.View
import com.imhanjie.support.getAppVersionCode
import com.imhanjie.support.getAppVersionName
import com.imhanjie.support.intentToBrowser
import com.imhanjie.v2ex.R
import com.imhanjie.v2ex.databinding.DialogAboutAppBinding
import com.imhanjie.widget.dialog.BaseCustomDialog

class AboutDialog(ctx: Context) : BaseCustomDialog<DialogAboutAppBinding>(ctx) {

    override val windowGravity: Int
        get() = Gravity.CENTER

    override val windowAnimation: Int
        get() = R.style.WidgetCenterDialogAnimation

    override fun initView(root: View) {
        vb.viewSourceCode.setOnClickListener { intentToBrowser(ctx, ctx.getString(R.string.app_github_repo_url)) }
        vb.cancel.setOnClickListener { dismiss() }
        vb.tvVersion.text = ctx.getString(R.string.app_about_version, getAppVersionName(ctx), getAppVersionCode(ctx))
    }

}