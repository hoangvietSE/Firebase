package com.soict.hoangviet.firebase.ui.presenter.impl;

import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.ui.presenter.TutorialPresenter
import com.soict.hoangviet.firebase.ui.view.TutorialView
import com.soict.hoangviet.firebase.ui.interactor.TutorialInteractor

import javax.inject.Inject

class TutorialPresenterImpl @Inject internal constructor(
    interactor: TutorialInteractor,
    sharePreference: SharePreference
) :
    BasePresenterImpl<TutorialView, TutorialInteractor>(
        mInteractor = interactor,
        mAppSharePreference = sharePreference
    ), TutorialPresenter {

}