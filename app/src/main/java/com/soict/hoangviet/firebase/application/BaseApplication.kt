package com.soict.hoangviet.firebase.application

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import com.crashlytics.android.Crashlytics
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.soict.hoangviet.firebase.BuildConfig
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.di.component.DaggerAppComponent
import com.soict.hoangviet.firebase.utils.AppConstant
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

    @Inject
    lateinit var mSharedPreferences: SharePreference

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
        getDeviceToken()
    }

    private fun getDeviceToken() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("myLog", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }
                    // Get new Instance ID token
                    val token = task.result?.token
                    mSharedPreferences.put(AppConstant.SharePreference.DEVICE_TOKEN, token ?: "")
                    putDeviceToken(token)
                    Log.d("myLog", token)
                })
        }
    }

    private fun putDeviceToken(token: String?) {
        val record = mapOf(
            AppConstant.SharePreference.DEVICE_TOKEN to token
        )
        FirebaseDatabase.getInstance()
            .getReference(AppConstant.DataBaseRef.USERS)
            .child(FirebaseAuth.getInstance().currentUser?.uid!!)
            .updateChildren(record)
    }

    private fun subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("corona_virus")
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
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