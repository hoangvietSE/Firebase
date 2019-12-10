package com.soict.hoangviet.firebase.ui.view

import com.soict.hoangviet.firebase.data.network.request.LoginRequest

interface LoginView : BaseView {
    fun onEmailEmpty()
    fun onEmailError()
    fun onPasswordError()
    fun onPasswordEmpty()
    fun onValidateSuccess(loginRequest: LoginRequest)
}