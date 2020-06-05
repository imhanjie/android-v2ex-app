package com.imhanjie.v2ex

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.imhanjie.support.AndroidUtils
import com.imhanjie.support.ext.toActivity
import com.imhanjie.v2ex.api.V2exApi
import com.imhanjie.v2ex.view.LoginActivity

class App : MultiDexApplication(), ViewModelStoreOwner {

    private val globalViewModelStore = ViewModelStore()

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
        AppCompatDelegate.setDefaultNightMode(
            getSharedPreferences("app_config", Context.MODE_PRIVATE).getInt("ui_mode", AppCompatDelegate.MODE_NIGHT_NO)
//                    PreferencesManager . getInstance ("app_config").getInt("ui_mode", AppCompatDelegate.MODE_NIGHT_NO)
        )
        INSTANCE = this
        AndroidUtils.install(this, BuildConfig.DEBUG)
        V2exApi.init {
            AppSession.getUserInfo().value!!.a2Cookie
        }
        Stetho.initializeWithDefaults(this)
    }

    override fun getViewModelStore(): ViewModelStore {
        return globalViewModelStore
    }

}