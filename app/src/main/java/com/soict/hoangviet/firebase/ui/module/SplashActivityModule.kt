package com.soict.hoangviet.firebase.ui.module

import com.soict.hoangviet.firebase.ui.interactor.SplashInteractor
import com.soict.hoangviet.firebase.ui.interactor.impl.SplashInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.SplashPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.SplashPresenterImpl
import com.soict.hoangviet.firebase.ui.view.SplashView
import dagger.Module
import dagger.Provides

@Module
class SplashActivityModule {
    @Provides
    internal fun provideSplashInteractor(splashInteractorImpl: SplashInteractorImpl)
            : SplashInteractor = splashInteractorImpl

    @Provides
    internal fun provideSplashPresenter(splashPresenterImpl: SplashPresenterImpl)
            : SplashPresenter = splashPresenterImpl

}