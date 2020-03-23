package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.ui.interactor.ConfirmInteractor
import com.soict.hoangviet.firebase.ui.presenter.ConfirmPresenter
import com.soict.hoangviet.firebase.ui.view.ConfirmView
import javax.inject.Inject

class ConfirmPresenterImpl @Inject internal constructor(
    confirmInteractor: ConfirmInteractor,
    sharePreference: SharePreference
) :
    BasePresenterImpl<ConfirmView, ConfirmInteractor>(
        mInteractor = confirmInteractor,
        mAppSharePreference = sharePreference
    ), ConfirmPresenter {

}