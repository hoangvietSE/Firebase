package com.soict.hoangviet.firebase.ui.module

import com.soict.hoangviet.firebase.ui.interactor.HomeInteractor
import com.soict.hoangviet.firebase.ui.interactor.impl.HomeInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.HomePresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.HomePresenterImpl
import dagger.Module
import dagger.Provides

@Module
class HomeFragmentModule {
    @Provides
    internal fun provideHomeInteractor(homeInteractorImpl: HomeInteractorImpl)
            : HomeInteractor = homeInteractorImpl

    @Provides
    internal fun provideHomePresenter(homePresenterImpl: HomePresenterImpl)
            : HomePresenter = homePresenterImpl

}