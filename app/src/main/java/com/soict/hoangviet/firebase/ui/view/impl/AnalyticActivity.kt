package com.soict.hoangviet.firebase.ui.view.impl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.utils.analytics.AnalyticsUtil
import kotlinx.android.synthetic.main.activity_analytic.*

class AnalyticActivity : AppCompatActivity() {
    companion object {
        val TAG = AnalyticActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytic)
        setListener()
    }

    private fun setListener() {
        btn_first.setOnClickListener {
            AnalyticsUtil.trackEvent("button_first")
        }
        btn_second.setOnClickListener {
            AnalyticsUtil.trackEvent("button_second")
        }
        btn_third.setOnClickListener {
            AnalyticsUtil.trackEvent("button_third")
        }
        btn_fourth.setOnClickListener {
            AnalyticsUtil.trackEvent("button_fourth")
        }
    }

    override fun onResume() {
        super.onResume()
        AnalyticsUtil.setUserId("user_02")
        AnalyticsUtil.setUserProperties("company", "BeetechSoftCompany")
        AnalyticsUtil.setUserProperties("email", "company@beetechsoft.com")
        AnalyticsUtil.trackScreen(this, TAG)
    }
}