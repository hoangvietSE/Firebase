package com.soict.hoangviet.firebase.application

import android.app.Application
import android.content.Context
import com.soict.hoangviet.firebase.BuildConfig
import com.soict.hoangviet.firebase.utils.LogUtil
import io.fabric.sdk.android.Fabric
import com.crashlytics.android.Crashlytics



class BaseApplication : Application() {
    companion object {
        lateinit var instance: BaseApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LogUtil.init(BuildConfig.DEBUG)
        enableDebugMode()
    }

    private fun enableDebugMode() {
        // [START crash_enable_debug_mode]
        val fabric = Fabric.Builder(this)
            .kits(Crashlytics())
            .debuggable(true)  // Enables Crashlytics debugger
            .build()
        Fabric.with(fabric)
        // [END crash_enable_debug_mode]
    }
}