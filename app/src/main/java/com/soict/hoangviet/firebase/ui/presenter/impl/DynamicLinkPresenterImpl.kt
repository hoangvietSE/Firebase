package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.ui.interactor.DynamicLinkInteractor
import com.soict.hoangviet.firebase.ui.presenter.DynamicLinkPresenter
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