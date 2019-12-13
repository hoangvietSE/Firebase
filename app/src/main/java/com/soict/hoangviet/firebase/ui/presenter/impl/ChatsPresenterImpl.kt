package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.ui.interactor.ChatsInteractor
import com.soict.hoangviet.firebase.ui.presenter.ChatsPresenter
import com.soict.hoangviet.firebase.ui.view.ChatsView

class ChatsPresenterImpl(mView: ChatsView, mInteractor: ChatsInteractor) :
        BasePresenterImpl<ChatsView, ChatsInteractor>(mView, mInteractor), ChatsPresenter {
}