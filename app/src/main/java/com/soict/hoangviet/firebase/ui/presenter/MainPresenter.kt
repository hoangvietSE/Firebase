package com.soict.hoangviet.firebase.ui.presenter

import com.soict.hoangviet.firebase.ui.interactor.MainInteractor
import com.soict.hoangviet.firebase.ui.view.MainView

interface MainPresenter : BasePresenter<MainView, MainInteractor> {
    fun setStatus(status: Int)
    fun clearDeviceToken()
}