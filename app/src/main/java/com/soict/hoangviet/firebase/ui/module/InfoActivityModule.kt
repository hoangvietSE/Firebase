package com.soict.hoangviet.firebase.ui.module;

import com.soict.hoangviet.firebase.ui.interactor.InfoInteractor;
import com.soict.hoangviet.firebase.ui.interactor.impl.InfoInteractorImpl;
import com.soict.hoangviet.firebase.ui.presenter.InfoPresenter;
import com.soict.hoangviet.firebase.ui.presenter.impl.InfoPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
class InfoActivityModule {
    @Provides
    internal fun provideInfoInteractor(mInteractor: InfoInteractorImpl): InfoInteractor =
        mInteractor

    @Provides
    internal fun provideInfoPresenter(mPresenter: InfoPresenterImpl): InfoPresenter = mPresenter
}
