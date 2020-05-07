package com.imhanjie.widget.dialog

import android.content.Context
import android.view.Gravity
import android.view.View
import com.imhanjie.widget.R
import com.imhanjie.widget.databinding.WidgetDialogLoadingBinding

class PureLoadingDialog(ctx: Context) : BaseCustomDialog<WidgetDialogLoadingBinding>(ctx) {

    override val windowGravity: Int
        get() = Gravity.CENTER

    override val windowAnimation: Int
        get() = R.style.WidgetCenterDialogAnimation

    override fun initView(root: View) {

    }

    fun update(done: Boolean) {
        if (done) {
            dismiss()
        } else {
            show()
        }
    }

    override fun show() {
        try {
            val activity = getActivity(context)
            if (activity == null || activity.isFinishing) {
                return
            }
            if (isShowing) {
                return
            }
            super.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dismiss() {
        try {
            val activity = getActivity(context)
            if (activity == null || activity.isFinishing) {
                return
            }
            if (!isShowing) {
                return
            }
            super.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}