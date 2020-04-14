package com.soict.hoangviet.firebase.ui.view.impl

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import com.soict.hoangviet.baseproject.extension.onAvoidDoubleClick
import com.soict.hoangviet.firebase.R
import io.fabric.sdk.android.Fabric

class CrashlyticActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crashlytics)
        enableAtRuntime()
        setUserId()
        setKey()
        setListener()
    }

    private fun setUserId() {
        // [START crash_set_user_id]
        Crashlytics.setUserIdentifier("user_02")
        // [END crash_set_user_id]
    }

    private fun enableAtRuntime() {
        // [START crash_enable_at_runtime]
        Fabric.with(this, Crashlytics())
        // [END crash_enable_at_runtime]
    }

    private fun setKey() {
        // [specific state of your app leading up to a crash]
        Crashlytics.setFloat("current_level", 1.0f)
    }

    private fun setListener() {
        forceACrash()
    }

    private fun forceACrash() {
        // [START crash_force_crash]
        val crashButton = Button(this)
        crashButton.text = "Crash!"
        crashButton.onAvoidDoubleClick {
            //            Crashlytics.getInstance().crash() // Force a crash
            logCaughtEx()
        }

        addContentView(
            crashButton, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        // [END crash_force_crash]
    }

    private fun logCaughtEx() {
        // [START crash_log_caught_ex]
        try {
            methodThatThrows()
        } catch (e: Exception) {
            Crashlytics.log(Log.DEBUG,"Exception","Error Happen. Try again!")
            Crashlytics.getInstance().crash()
            // handle your exception here
        }
        // [END crash_log_caught_ex]
    }

    private fun methodThatThrows() {
        throw Exception()
    }
}