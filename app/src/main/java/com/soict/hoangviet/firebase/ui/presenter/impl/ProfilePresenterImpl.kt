package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.baseproject.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.application.BaseApplication
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.interactor.ProfileInteractor
import com.soict.hoangviet.firebase.ui.presenter.ProfilePresenter
import com.soict.hoangviet.firebase.ui.view.ProfileView
import com.soict.hoangviet.firebase.utils.AppConstant
import javax.inject.Inject

class ProfilePresenterImpl @Inject internal constructor(
    profileInteractor: ProfileInteractor,
    sharePreference: SharePreference
) : BasePresenterImpl<ProfileView, ProfileInteractor>(
    mInteractor = profileInteractor,
    mAppSharePreference = sharePreference
), ProfilePresenter {
    override fun getCurrentUser() {
        val user = mAppSharePreference?.get(AppConstant.SharePreference.USER, User::class.java)
        mView?.showUserInfo(user!!)
    }

}