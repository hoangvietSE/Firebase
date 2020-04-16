package com.soict.hoangviet.firebase.ui.presenter.impl;

import com.soict.hoangviet.firebase.data.sharepreference.SharePreference;
import com.soict.hoangviet.firebase.ui.presenter.InfoPresenter;
import com.soict.hoangviet.firebase.ui.view.InfoView;
import com.soict.hoangviet.firebase.ui.interactor.InfoInteractor;

import javax.inject.Inject;

class InfoPresenterImpl @Inject internal constructor(
    interactor: InfoInteractor,
    sharePreference: SharePreference
) :
    BasePresenterImpl<InfoView, InfoInteractor>(
        mInteractor = interactor,
        mAppSharePreference = sharePreference
    ), InfoPresenter {

}