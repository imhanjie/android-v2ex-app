package com.imhanjie.widget.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.imhanjie.widget.R
import com.imhanjie.widget.common.getVBClass

@Suppress("LeakingThis", "UNCHECKED_CAST")
abstract class BaseCustomDialog<VB : ViewBinding>(val ctx: Context) : Dialog(ctx, R.style.Widget_Pure_Custom_Dialog) {

    abstract fun initView(root: View)

    open val windowAnimation: Int = R.style.WidgetBottomDialogAnimation
    open val windowGravity = Gravity.BOTTOM

    protected var vb: VB

    init {
        val vbClass = getVBClass<VB>(javaClass)
        val method = vbClass.getMethod("inflate", LayoutInflater::class.java)
        vb = method.invoke(null, layoutInflater) as VB
        setContentView(vb.root)

        window?.let {
            val lp = it.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            lp.gravity = windowGravity
            it.attributes = lp
            it.setWindowAnimations(windowAnimation)
        }
    }

    override fun onStart() {
        super.onStart()
        initView(vb.root)
    }

    open fun getActivity(context: Context?): Activity? {
        return when (context) {
            null -> {
                null
            }
            is Activity -> {
                context
            }
            is ContextWrapper -> {
                getActivity(context.baseContext)
            }
            else -> null
        }
    }

}