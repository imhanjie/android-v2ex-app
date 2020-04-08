package com.imhanjie.v2ex

import android.util.Log
import androidx.multidex.MultiDexApplication

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Log.e("bingo", "start!")
    }

}