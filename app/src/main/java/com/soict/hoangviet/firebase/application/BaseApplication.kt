package com.soict.hoangviet.firebase.application

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.crashlytics.android.Crashlytics
import com.soict.hoangviet.firebase.BuildConfig
import com.soict.hoangviet.firebase.di.component.DaggerAppComponent
import com.soict.hoangviet.firebase.utils.LogUtil
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import io.fabric.sdk.android.Fabric
import javax.inject.Inject


class BaseApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {
    @Inject
    internal lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

    companion object {
        lateinit var instance: BaseApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LogUtil.init(BuildConfig.DEBUG)
        enableDebugMode()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

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

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }
}