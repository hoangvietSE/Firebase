package com.soict.hoangviet.firebase.ui.view

import com.soict.hoangviet.firebase.data.network.request.RegisterRequest

interface RegisterView : BaseView {
    fun onUsernameEmpty()
    fun onUsernameError()
    fun onEmailEmpty()
    fun onEmailError()
    fun onPasswordError()
    fun onPasswordEmpty()
    fun onValidateSuccess(registerRequest: RegisterRequest)
}