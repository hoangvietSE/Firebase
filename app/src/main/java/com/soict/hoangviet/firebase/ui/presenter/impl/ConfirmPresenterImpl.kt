package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.ui.interactor.ConfirmInteractor
import com.soict.hoangviet.firebase.ui.presenter.ConfirmPresenter
import com.soict.hoangviet.firebase.ui.view.ConfirmView

class ConfirmPresenterImpl(mView: ConfirmView, mInteractor: ConfirmInteractor) :
        BasePresenterImpl<ConfirmView, ConfirmInteractor>(mView, mInteractor), ConfirmPresenter {
}