package com.soict.hoangviet.firebase.ui.presenter

import com.soict.hoangviet.firebase.data.network.request.RegisterRequest

interface RegisterPresenter : BasePresenter {
    fun validateRegister(registerRequest: RegisterRequest)
}