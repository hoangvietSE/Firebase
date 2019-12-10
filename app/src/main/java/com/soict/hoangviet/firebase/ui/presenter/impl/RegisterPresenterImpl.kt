package com.soict.hoangviet.firebase.ui.presenter.impl

import android.text.TextUtils
import com.soict.hoangviet.firebase.data.network.request.RegisterRequest
import com.soict.hoangviet.firebase.extension.isValidateEmail
import com.soict.hoangviet.firebase.extension.isValidatePassword
import com.soict.hoangviet.firebase.ui.interactor.RegisterInteractor
import com.soict.hoangviet.firebase.ui.presenter.RegisterPresenter
import com.soict.hoangviet.firebase.ui.view.RegisterView

class RegisterPresenterImpl(mView: RegisterView, mInteractor: RegisterInteractor) :
        BasePresenterImpl<RegisterView, RegisterInteractor>(mView, mInteractor), RegisterPresenter {
    override fun validateRegister(registerRequest: RegisterRequest) {
        if (TextUtils.isEmpty(registerRequest.username)) {
            mView!!.onUsernameEmpty()
            return
        }
        if (TextUtils.isEmpty(registerRequest.email)) {
            mView!!.onEmailEmpty()
            return
        }
        if (!registerRequest.email.isValidateEmail()) {
            mView!!.onEmailError()
            return
        }
        if (TextUtils.isEmpty(registerRequest.password)) {
            mView!!.onPasswordEmpty()
            return
        }
        if (!registerRequest.password.isValidatePassword()) {
            mView!!.onPasswordError()
            return
        }
        mView!!.onValidateSuccess(registerRequest)
    }
}