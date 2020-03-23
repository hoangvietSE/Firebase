package com.soict.hoangviet.firebase.ui.presenter.impl

import android.text.TextUtils
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.data.network.request.LoginRequest
import com.soict.hoangviet.firebase.data.network.response.User
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
        getCurrentUser(object : RealTimeDatabaseListener<User> {
            override fun onLoadSuccess(data: User) {
                mAppSharePreference?.put(AppConstant.SharePreference.USER, data)
            }

            override fun onLoadError() {
            }

        })
    }
}