package com.imhanjie.v2ex

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.imhanjie.support.PreferencesManager
import com.imhanjie.support.ext.getResColor
import com.imhanjie.support.ext.toast
import com.imhanjie.support.statusbar.StatusBarUtil
import com.imhanjie.v2ex.common.GlobalViewModel
import com.imhanjie.v2ex.common.SpConstants
import com.imhanjie.v2ex.model.VmEvent
import com.imhanjie.v2ex.vm.BaseViewModel
import com.imhanjie.widget.common.getVBClass
import com.imhanjie.widget.dialog.PureLoadingDialog

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var vb: VB

    private val configSp: PreferencesManager = PreferencesManager.getInstance(SpConstants.FILE_APP_CONFIG)
    protected lateinit var loadingDialog: PureLoadingDialog
    protected lateinit var globalViewModel: GlobalViewModel

    abstract fun getViewModels(): List<BaseViewModel>

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vbClass = getVBClass<VB>(javaClass)
        val method = vbClass.getMethod("inflate", LayoutInflater::class.java)
        vb = method.invoke(null, layoutInflater) as VB
        setContentView(vb.root)

        loadingDialog = PureLoadingDialog(this).apply {
            setCancelable(false)
        }
        for (vm in getViewModels()) {
            vm.event.observe(this) {
                when (it.event) {
                    VmEvent.Event.SHOW_LOADING -> loadingDialog.update(false)
                    VmEvent.Event.HIDE_LOADING -> loadingDialog.update(true)
                    VmEvent.Event.TOAST, VmEvent.Event.ERROR -> toast(it.text)
                }
            }
        }

        globalViewModel = ViewModelProvider(applicationContext as V2exApp).get(GlobalViewModel::class.java)

        when (configSp.getInt(SpConstants.UI_MODE, AppCompatDelegate.MODE_NIGHT_NO)) {
            AppCompatDelegate.MODE_NIGHT_NO -> {
                StatusBarUtil.setLightMode(this)
            }
            AppCompatDelegate.MODE_NIGHT_YES -> {
                window.navigationBarColor = getResColor(R.color.widget_background_1)
            }
        }

    }

}