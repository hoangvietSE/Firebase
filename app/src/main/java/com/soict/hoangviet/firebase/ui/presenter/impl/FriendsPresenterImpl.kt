package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.ui.interactor.FriendsInteractor
import com.soict.hoangviet.firebase.ui.presenter.FriendsPresenter
import com.soict.hoangviet.firebase.ui.view.FriendsView
import javax.inject.Inject

class FriendsPresenterImpl @Inject internal constructor(
    friendsInteractor: FriendsInteractor,
    sharePreference: SharePreference
) :
    BasePresenterImpl<FriendsView, FriendsInteractor>(
        mInteractor = friendsInteractor,
        mAppSharePreference = sharePreference
    ), FriendsPresenter {

}