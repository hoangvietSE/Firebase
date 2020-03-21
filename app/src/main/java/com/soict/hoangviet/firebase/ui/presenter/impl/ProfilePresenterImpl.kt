package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.application.BaseApplication
import com.soict.hoangviet.firebase.ui.interactor.ProfileInteractor
import com.soict.hoangviet.firebase.ui.presenter.ProfilePresenter
import com.soict.hoangviet.firebase.ui.view.ProfileView

class ProfilePresenterImpl(mView: ProfileView, mMainInteractor: ProfileInteractor) : BasePresenterImpl<ProfileView, ProfileInteractor>(mView, mMainInteractor), ProfilePresenter {
    override fun getCurrentUser() {
        val user = AppSharePreference.getInstance(BaseApplication.instance).getUser()
        mView?.showUserInfo(user!!)
    }

}