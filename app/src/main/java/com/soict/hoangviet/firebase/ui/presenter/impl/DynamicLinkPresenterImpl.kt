package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.baseproject.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.ui.interactor.BaseInterator
import com.soict.hoangviet.firebase.ui.interactor.ConfirmInteractor
import com.soict.hoangviet.firebase.ui.interactor.DynamicLinkInteractor
import com.soict.hoangviet.firebase.ui.presenter.ConfirmPresenter
import com.soict.hoangviet.firebase.ui.presenter.DynamicLinkPresenter
import com.soict.hoangviet.firebase.ui.view.BaseView
import com.soict.hoangviet.firebase.ui.view.ConfirmView
import com.soict.hoangviet.firebase.ui.view.DynamicLinkView
import javax.inject.Inject

class DynamicLinkPresenterImpl @Inject internal constructor(
    dynamicLinkInteractor: DynamicLinkInteractor,
    sharePreference: SharePreference
) :
    BasePresenterImpl<DynamicLinkView, DynamicLinkInteractor>(
        mInteractor = dynamicLinkInteractor,
        mAppSharePreference = sharePreference
    ), DynamicLinkPresenter {

}