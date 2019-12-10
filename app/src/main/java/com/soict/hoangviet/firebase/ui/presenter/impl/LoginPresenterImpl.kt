package com.soict.hoangviet.firebase.ui.presenter.impl

import android.text.TextUtils
import com.soict.hoangviet.firebase.data.network.request.LoginRequest
import com.soict.hoangviet.firebase.extension.isValidateEmail
import com.soict.hoangviet.firebase.extension.isValidatePassword
import com.soict.hoangviet.firebase.ui.interactor.LoginInteractor
import com.soict.hoangviet.firebase.ui.presenter.LoginPresenter
import com.soict.hoangviet.firebase.ui.view.LoginView

class LoginPresenterImpl(mView: LoginView, mInteractor: LoginInteractor) :
    BasePresenterImpl<LoginView, LoginInteractor>(mView, mInteractor), LoginPresenter {
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

}