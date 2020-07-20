package com.imhanjie.v2ex

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.imhanjie.support.AndroidUtils
import com.imhanjie.support.PreferencesManager
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.api.V2exApi
import com.imhanjie.v2ex.common.SpConstants
import com.imhanjie.v2ex.view.LoginActivity

class App : MultiDexApplication(), ViewModelStoreOwner {

    private val globalViewModelStore = ViewModelStore()

    override fun getViewModelStore(): ViewModelStore {
        return globalViewModelStore
    }

    companion object {
        lateinit var INSTANCE: Application

        fun logout() {
            AppSession.clear()
            INSTANCE.toActivity<LoginActivity> { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        AndroidUtils.install(this, BuildConfig.DEBUG)
        // set ui mode
        AppCompatDelegate.setDefaultNightMode(
            PreferencesManager.getInstance(SpConstants.FILE_APP_CONFIG).getInt(SpConstants.UI_MODE, AppCompatDelegate.MODE_NIGHT_NO)
        )
        V2exApi.init {
            AppSession.getUserInfo().value!!.a2Cookie
        }
        Stetho.initializeWithDefaults(this)
    }

}