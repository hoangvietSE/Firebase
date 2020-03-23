package com.soict.hoangviet.firebase.ui.module

import com.soict.hoangviet.firebase.ui.interactor.MessageInteractor
import com.soict.hoangviet.firebase.ui.interactor.impl.MessageInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.MessagePresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.MessagePresenterImpl
import com.soict.hoangviet.firebase.ui.view.MessageView
import dagger.Module
import dagger.Provides

@Module
class MessageActivityModule {
    @Provides
    internal fun provideMessageInteractor(messageInteractorImpl: MessageInteractorImpl)
            : MessageInteractor = messageInteractorImpl

    @Provides
    internal fun provideMessagePresenter(messagePresenterImpl: MessagePresenterImpl)
            : MessagePresenter = messagePresenterImpl

}