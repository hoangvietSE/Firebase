package com.soict.hoangviet.firebase.ui.presenter

import com.soict.hoangviet.firebase.data.network.request.LoginRequest
import com.soict.hoangviet.firebase.ui.interactor.LoginInteractor
import com.soict.hoangviet.firebase.ui.view.LoginView

interface LoginPresenter : BasePresenter<LoginView, LoginInteractor> {
    fun validateLogin(loginRequest: LoginRequest)
    fun saveCurrentUser()
    fun removeListener()
}