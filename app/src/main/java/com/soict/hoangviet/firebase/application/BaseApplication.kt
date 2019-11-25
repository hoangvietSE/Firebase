package com.soict.hoangviet.firebase.application

import android.app.Application
import android.content.Context
import com.soict.hoangviet.firebase.BuildConfig
import com.soict.hoangviet.firebase.utils.LogUtil

class BaseApplication : Application() {
    companion object {
        lateinit var instance: BaseApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LogUtil.init(BuildConfig.DEBUG)

    }

    fun getContext(): Context {
        return instance
    }
}