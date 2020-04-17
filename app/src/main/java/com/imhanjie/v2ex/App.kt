package com.imhanjie.v2ex

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.imhanjie.support.AndroidUtils

class App : MultiDexApplication() {

    companion object {
        lateinit var INSTANCE: Application
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        AndroidUtils.install(this, BuildConfig.DEBUG)
        Stetho.initializeWithDefaults(this)
    }

}