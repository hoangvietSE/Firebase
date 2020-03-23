package com.soict.hoangviet.firebase.ui.presenter

import com.soict.hoangviet.firebase.ui.interactor.HomeInteractor
import com.soict.hoangviet.firebase.ui.view.HomeView

interface HomePresenter : BasePresenter<HomeView, HomeInteractor> {
    fun getAllChatUsers()
}