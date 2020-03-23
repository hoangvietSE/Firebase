package com.soict.hoangviet.firebase.ui.module

import com.soict.hoangviet.firebase.ui.interactor.RegisterInteractor
import com.soict.hoangviet.firebase.ui.interactor.impl.RegisterInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.RegisterPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.RegisterPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class RegisterActivityModule {
    @Provides
    internal fun provideRegisterInteractor(registerInteractorImpl: RegisterInteractorImpl)
            : RegisterInteractor = registerInteractorImpl

    @Provides
    internal fun provideRegisterPresenter(registerPresenterImpl: RegisterPresenterImpl)
            : RegisterPresenter = registerPresenterImpl

}