package com.soict.hoangviet.firebase.ui.module

import com.soict.hoangviet.firebase.ui.interactor.LoginInteractor
import com.soict.hoangviet.firebase.ui.interactor.impl.LoginInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.LoginPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.LoginPresenterImpl
import com.soict.hoangviet.firebase.ui.view.LoginView
import dagger.Module
import dagger.Provides

@Module
class LoginActivityModule {
    @Provides
    internal fun provideLoginInteractor(loginInteractorImpl: LoginInteractorImpl)
            : LoginInteractor = loginInteractorImpl

    @Provides
    internal fun provideLoginPresenter(loginPresenterImpl: LoginPresenterImpl)
            : LoginPresenter = loginPresenterImpl

}