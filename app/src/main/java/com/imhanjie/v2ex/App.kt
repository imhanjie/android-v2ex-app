package com.imhanjie.v2ex

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.imhanjie.support.AndroidUtils
import com.imhanjie.support.PreferencesManager

class App : MultiDexApplication() {

    companion object {
        lateinit var INSTANCE: Application
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        AndroidUtils.install(this, BuildConfig.DEBUG)
        AppCompatDelegate.setDefaultNightMode(
            PreferencesManager.getInstance("app_config").getInt("ui_mode", AppCompatDelegate.MODE_NIGHT_NO)
        )
        Stetho.initializeWithDefaults(this)
    }

}