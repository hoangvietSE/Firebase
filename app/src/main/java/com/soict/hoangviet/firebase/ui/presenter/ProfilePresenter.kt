package com.soict.hoangviet.firebase.ui.presenter

import com.soict.hoangviet.firebase.ui.interactor.ProfileInteractor
import com.soict.hoangviet.firebase.ui.view.ProfileView

interface ProfilePresenter : BasePresenter<ProfileView, ProfileInteractor> {
    fun getCurrentUser()
}