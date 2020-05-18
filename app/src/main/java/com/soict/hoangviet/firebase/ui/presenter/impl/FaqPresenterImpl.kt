package com.soict.hoangviet.firebase.ui.presenter.impl;

import com.soict.hoangviet.firebase.data.sharepreference.SharePreference;
import com.soict.hoangviet.firebase.ui.presenter.FaqPresenter;
import com.soict.hoangviet.firebase.ui.view.FaqView;
import com.soict.hoangviet.firebase.ui.interactor.FaqInteractor;

import javax.inject.Inject;

class FaqPresenterImpl @Inject internal constructor(
    interactor: FaqInteractor,
    sharePreference: SharePreference
) :
    BasePresenterImpl<FaqView, FaqInteractor>(
        mInteractor = interactor,
        mAppSharePreference = sharePreference
    ), FaqPresenter {

}