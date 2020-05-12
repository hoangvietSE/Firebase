package com.soict.hoangviet.firebase.ui.presenter.impl;

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.soict.hoangviet.firebase.builder.DatabaseFirebase
import com.soict.hoangviet.firebase.data.local.entity.NotificationSetting
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference;
import com.soict.hoangviet.firebase.ui.presenter.NotificationPresenter;
import com.soict.hoangviet.firebase.ui.view.NotificationView;
import com.soict.hoangviet.firebase.ui.interactor.NotificationInteractor;
import com.soict.hoangviet.firebase.utils.AppConstant

import javax.inject.Inject;

class NotificationPresenterImpl @Inject internal constructor(
    interactor: NotificationInteractor,
    sharePreference: SharePreference
) :
    BasePresenterImpl<NotificationView, NotificationInteractor>(
        mInteractor = interactor,
        mAppSharePreference = sharePreference
    ), NotificationPresenter {
    private var mSettingListener: Pair<DatabaseReference, ValueEventListener>? = null
    override fun setNotificationStatus(checked: Boolean) {
        val notificationSetting = NotificationSetting()
        notificationSetting.enable = if (checked) 1 else 0
        FirebaseDatabase.getInstance()
            .getReference("Notifications")
            .child(
                mAppSharePreference?.get(
                    AppConstant.SharePreference.USER,
                    User::class.java
                )?.id.toString()
            )
            .setValue(notificationSetting)
    }

    override fun fetchData() {
        mSettingListener = DatabaseFirebase.Builder()
            .reference("Notifications")
            .child(
                mAppSharePreference?.get(
                    AppConstant.SharePreference.USER,
                    User::class.java
                )?.id.toString()
            )
            .onDataChange {
                val data = it.getValue(NotificationSetting::class.java)
                data?.let { mView?.updateNotificationSetting(it) }
            }
            .onCancelled {
                Log.d("myLog", it.message)
            }
            .build()
    }

    override fun onDetach() {
        super.onDetach()
        mSettingListener?.let {
            it.first.removeEventListener(it.second)
        }
    }

}