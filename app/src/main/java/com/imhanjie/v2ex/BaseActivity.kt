package com.imhanjie.v2ex

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.viewbinding.ViewBinding
import com.imhanjie.support.PreferencesManager
import com.imhanjie.support.statusbar.StatusBarUtil
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var vb: VB

    protected val configSp: PreferencesManager = PreferencesManager.getInstance("app_config")

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz: Class<VB> = type.actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        vb = method.invoke(null, layoutInflater) as VB
        setContentView(vb.root)

        if (configSp.getInt("ui_mode", AppCompatDelegate.MODE_NIGHT_NO) == AppCompatDelegate.MODE_NIGHT_NO) {
            StatusBarUtil.setLightMode(this)
        }
//
//        when (configSp.getInt("ui_mode", AppCompatDelegate.MODE_NIGHT_NO)) {
//            AppCompatDelegate.MODE_NIGHT_YES -> AppCompatDelegate.MODE_NIGHT_NO
//            AppCompatDelegate.MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_YES
//            else -> AppCompatDelegate.MODE_NIGHT_NO
//        }
//        StatusBarUtil.setLightMode(this)
    }

}