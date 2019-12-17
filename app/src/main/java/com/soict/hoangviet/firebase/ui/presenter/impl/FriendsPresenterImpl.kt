package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.ui.interactor.FriendsInteractor
import com.soict.hoangviet.firebase.ui.presenter.FriendsPresenter
import com.soict.hoangviet.firebase.ui.view.FriendsView

class FriendsPresenterImpl(mView: FriendsView, mInteractor: FriendsInteractor) :
        BasePresenterImpl<FriendsView, FriendsInteractor>(mView, mInteractor), FriendsPresenter {
}