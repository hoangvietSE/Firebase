package com.soict.hoangviet.firebase.ui.presenter.impl

import android.text.TextUtils
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import com.soict.hoangviet.firebase.data.network.request.LoginRequest
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.extension.isValidateEmail
import com.soict.hoangviet.firebase.extension.isValidatePassword
import com.soict.hoangviet.firebase.ui.interactor.LoginInteractor
import com.soict.hoangviet.firebase.ui.presenter.LoginPresenter
import com.soict.hoangviet.firebase.ui.view.LoginView
import com.soict.hoangviet.firebase.utils.AppConstant
import javax.inject.Inject

class LoginPresenterImpl @Inject internal constructor(
    loginInteractor: LoginInteractor,
    sharePreference: SharePreference
) : BasePresenterImpl<LoginView, LoginInteractor>(
    mInteractor = loginInteractor,
    mAppSharePreference = sharePreference
), LoginPresenter {
    override fun validateLogin(loginRequest: LoginRequest) {
        if (TextUtils.isEmpty(loginRequest.email)) {
            mView!!.onEmailEmpty()
            return
        }
        if (!loginRequest.email.isValidateEmail()) {
            mView!!.onEmailError()
            return
        }
        if (TextUtils.isEmpty(loginRequest.password)) {
            mView!!.onPasswordEmpty()
            return
        }
        if (!loginRequest.password.isValidatePassword()) {
            mView!!.onPasswordError()
            return
        }
        mView!!.onValidateSuccess(loginRequest)
    }

    override fun saveCurrentUser() {
        getCurrentUser({
            mAppSharePreference?.put(AppConstant.SharePreference.USER, it)
            getView()?.goToMainScreen()
        }, {
        })
        getDeviceToken()
    }

    override fun removeListener() {
        removeValueListener()
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
                    mAppSharePreference?.put(AppConstant.SharePreference.DEVICE_TOKEN, token ?: "")
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
}