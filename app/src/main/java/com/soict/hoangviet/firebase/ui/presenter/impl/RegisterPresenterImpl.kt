package com.soict.hoangviet.firebase.ui.presenter.impl

import android.text.TextUtils
import com.soict.hoangviet.baseproject.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.data.network.request.RegisterRequest
import com.soict.hoangviet.firebase.extension.isValidateEmail
import com.soict.hoangviet.firebase.extension.isValidatePassword
import com.soict.hoangviet.firebase.extension.isValidatePhoneNumber
import com.soict.hoangviet.firebase.ui.interactor.RegisterInteractor
import com.soict.hoangviet.firebase.ui.presenter.RegisterPresenter
import com.soict.hoangviet.firebase.ui.view.RegisterView
import javax.inject.Inject

class RegisterPresenterImpl @Inject internal constructor(
    registerInteractor: RegisterInteractor,
    sharePreference: SharePreference
) : BasePresenterImpl<RegisterView, RegisterInteractor>(
    mInteractor = registerInteractor,
    mAppSharePreference = sharePreference
), RegisterPresenter {
    override fun validateRegister(registerRequest: RegisterRequest) {
        if (TextUtils.isEmpty(registerRequest.fullName)) {
            mView!!.onFullNameEmpty()
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
        if (TextUtils.isEmpty(registerRequest.phoneNumber)) {
            mView!!.onPhoneEmpty()
            return
        }
        if (!registerRequest.phoneNumber.isValidatePhoneNumber()) {
            mView!!.onPhoneError()
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