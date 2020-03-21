package com.soict.hoangviet.firebase.ui.module

import com.soict.hoangviet.firebase.ui.interactor.MainInteractor
import com.soict.hoangviet.firebase.ui.interactor.impl.MainInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.MainPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.MainPresenterImpl
import com.soict.hoangviet.firebase.ui.view.MainView
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    internal fun provideMainInteractor(mMainInteractor: MainInteractorImpl)
            : MainInteractor = mMainInteractor

    @Provides
    internal fun provideMainPresenter(mainPresenterImpl: MainPresenterImpl)
            : MainPresenter = mainPresenterImpl

}