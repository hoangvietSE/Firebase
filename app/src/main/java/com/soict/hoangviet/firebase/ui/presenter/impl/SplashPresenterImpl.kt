package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.interactor.SplashInteractor
import com.soict.hoangviet.firebase.ui.presenter.SplashPresenter
import com.soict.hoangviet.firebase.ui.view.SplashView
import com.soict.hoangviet.firebase.utils.AppConstant
import javax.inject.Inject

class SplashPresenterImpl @Inject internal constructor(
    splashInteractor: SplashInteractor,
    sharePreference: SharePreference
) : BasePresenterImpl<SplashView, SplashInteractor>(
    mInteractor = splashInteractor,
    mAppSharePreference = sharePreference
), SplashPresenter {
    override fun saveCurrentUser() {
        getCurrentUser(object : RealTimeDatabaseListener<User> {
            override fun onLoadSuccess(data: User) {
                removeValueListener()
                mAppSharePreference?.put(AppConstant.SharePreference.USER, data)
                mView?.goToHomeScreen()
            }

            override fun onLoadError() {
            }

        })
    }
}