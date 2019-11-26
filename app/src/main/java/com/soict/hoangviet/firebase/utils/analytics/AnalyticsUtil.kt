package com.soict.hoangviet.firebase.utils.analytics

import android.app.Activity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.soict.hoangviet.firebase.application.BaseApplication

object AnalyticsUtil {
    private const val EVENT_CATEGORY = "event_category"
    private const val EVETN_ACTION = "event_action"
    private const val CATEGORY_BUTTON = "button"
    private const val ACTION_TAP = "tap"
    private val firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(BaseApplication.instance)

    fun trackEventItemClick(id: String, name: String, contentType: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    fun trackEvent(eventName: String) {
        val bundle = Bundle()
        bundle.putString(EVENT_CATEGORY, CATEGORY_BUTTON)
        bundle.putString(EVETN_ACTION, ACTION_TAP)
        firebaseAnalytics.logEvent(eventName, bundle)
    }

    fun trackScreen(activity: Activity, name: String) {
        firebaseAnalytics.setCurrentScreen(activity, name, null)
    }

    fun setUserProperties(name: String, value: String) {
        firebaseAnalytics.setUserProperty(name, value)
    }

    fun setUserId(userId: String) {
        firebaseAnalytics.setUserId(userId)
    }

}