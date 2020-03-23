package com.soict.hoangviet.firebase.ui.presenter

import com.soict.hoangviet.firebase.ui.interactor.SplashInteractor
import com.soict.hoangviet.firebase.ui.view.SplashView

interface SplashPresenter : BasePresenter<SplashView, SplashInteractor> {
    fun saveCurrentUser()
}