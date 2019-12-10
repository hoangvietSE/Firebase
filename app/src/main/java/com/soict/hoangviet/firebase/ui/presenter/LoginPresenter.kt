package com.soict.hoangviet.firebase.ui.presenter

import com.soict.hoangviet.firebase.data.network.request.LoginRequest

interface LoginPresenter : BasePresenter {
    fun validateLogin(loginRequest: LoginRequest)
}