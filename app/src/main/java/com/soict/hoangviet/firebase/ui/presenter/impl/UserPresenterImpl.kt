package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.ui.interactor.UserInteractor
import com.soict.hoangviet.firebase.ui.presenter.UserPresenter
import com.soict.hoangviet.firebase.ui.view.UserView

class UserPresenterImpl(mView: UserView, mInteractor: UserInteractor) :
        BasePresenterImpl<UserView, UserInteractor>(mView, mInteractor), UserPresenter {
}