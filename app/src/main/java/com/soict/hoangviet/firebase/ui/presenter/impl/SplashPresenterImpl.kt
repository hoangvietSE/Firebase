package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.application.BaseApplication
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.interactor.SplashInteractor
import com.soict.hoangviet.firebase.ui.presenter.SplashPresenter
import com.soict.hoangviet.firebase.ui.view.SplashView

class SplashPresenterImpl(mView: SplashView, mInteractor: SplashInteractor) : BasePresenterImpl<SplashView, SplashInteractor>(mView, mInteractor), SplashPresenter {
    override fun saveCurrentUser() {
        getCurrentUser(object : RealTimeDatabaseListener<User>{
            override fun onLoadSuccess(data: User) {
                removeValueListener()
                AppSharePreference.getInstance(BaseApplication.instance).setUser(data)
                mView?.goToHomeScreen()
            }

            override fun onLoadError() {
            }

        })
    }
}