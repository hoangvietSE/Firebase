package com.soict.hoangviet.firebase.ui.view

import com.soict.hoangviet.firebase.data.network.request.RegisterRequest

interface RegisterView : BaseView {
    fun onFullNameEmpty()
    fun onEmailEmpty()
    fun onEmailError()
    fun onPhoneEmpty()
    fun onPhoneError()
    fun onPasswordError()
    fun onPasswordEmpty()
    fun onValidateSuccess(registerRequest: RegisterRequest)
}