package com.imhanjie.v2ex

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.observe
import androidx.viewbinding.ViewBinding
import com.imhanjie.support.PreferencesManager
import com.imhanjie.support.ext.getResColor
import com.imhanjie.support.ext.toast
import com.imhanjie.support.statusbar.StatusBarUtil
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.widget.dialog.PureLoadingDialog
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var vb: VB

    private val configSp: PreferencesManager = PreferencesManager.getInstance("app_config")
    protected lateinit var loadingDialog: PureLoadingDialog

    abstract fun initViewModels(): List<BaseViewModel>

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz: Class<VB> = type.actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        vb = method.invoke(null, layoutInflater) as VB
        setContentView(vb.root)

        loadingDialog = PureLoadingDialog(this).apply {
            setCancelable(false)
        }
        for (vm in initViewModels()) {
            vm.error.observe(this) { toast(it) }
            vm.toast.observe(this) { toast(it) }
            vm.loadingDialogState.observe(this) { loadingDialog.update(!it) }
        }

        when (configSp.getInt("ui_mode", AppCompatDelegate.MODE_NIGHT_NO)) {
            AppCompatDelegate.MODE_NIGHT_NO -> {
                StatusBarUtil.setLightMode(this)
            }
            AppCompatDelegate.MODE_NIGHT_YES -> {
                window.navigationBarColor = getResColor(R.color.widget_background_1)
            }
        }

    }

}