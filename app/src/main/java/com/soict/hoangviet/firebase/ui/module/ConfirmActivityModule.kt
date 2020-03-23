package com.soict.hoangviet.firebase.ui.module

import com.soict.hoangviet.firebase.ui.interactor.ConfirmInteractor
import com.soict.hoangviet.firebase.ui.interactor.impl.ConfirmInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.ConfirmPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.ConfirmPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class ConfirmActivityModule {
    @Provides
    internal fun provideConfirmInteractor(confirmInteractor: ConfirmInteractorImpl)
            : ConfirmInteractor = confirmInteractor

    @Provides
    internal fun provideConfirmPresenter(confirmPresenterImpl: ConfirmPresenterImpl)
            : ConfirmPresenter = confirmPresenterImpl
}