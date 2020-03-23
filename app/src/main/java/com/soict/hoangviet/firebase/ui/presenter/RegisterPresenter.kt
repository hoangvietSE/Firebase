package com.soict.hoangviet.firebase.ui.presenter

import com.soict.hoangviet.firebase.data.network.request.RegisterRequest
import com.soict.hoangviet.firebase.ui.interactor.RegisterInteractor
import com.soict.hoangviet.firebase.ui.view.RegisterView

interface RegisterPresenter : BasePresenter<RegisterView, RegisterInteractor> {
    fun validateRegister(registerRequest: RegisterRequest)
}