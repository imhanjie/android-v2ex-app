package com.imhanjie.v2ex

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.imhanjie.support.AndroidUtils
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.view.LoginActivity

class App : MultiDexApplication() {

    companion object {
        lateinit var INSTANCE: Application

        fun launchLoginPage() {
            // TODO 过滤多次调用
            INSTANCE.toActivity<LoginActivity>(
                mapOf(
                    "key" to "value"
                )
            ) { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(
            getSharedPreferences("app_config", Context.MODE_PRIVATE).getInt("ui_mode", AppCompatDelegate.MODE_NIGHT_NO)
//                    PreferencesManager . getInstance ("app_config").getInt("ui_mode", AppCompatDelegate.MODE_NIGHT_NO)
        )
        INSTANCE = this
        AndroidUtils.install(this, BuildConfig.DEBUG)
        Stetho.initializeWithDefaults(this)
    }

}