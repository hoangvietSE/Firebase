package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.ui.interactor.MessageInteractor
import com.soict.hoangviet.firebase.ui.presenter.MessagePresenter
import com.soict.hoangviet.firebase.ui.view.MessageView

class MessagePresenterImpl(mView: MessageView, mInteractor: MessageInteractor) :
        BasePresenterImpl<MessageView, MessageInteractor>(mView, mInteractor), MessagePresenter {
}