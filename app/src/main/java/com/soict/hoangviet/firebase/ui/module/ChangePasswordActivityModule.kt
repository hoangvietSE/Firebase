package com.soict.hoangviet.firebase.ui.module;

import com.soict.hoangviet.firebase.ui.interactor.ChangePasswordInteractor;
import com.soict.hoangviet.firebase.ui.interactor.impl.ChangePasswordInteractorImpl;
import com.soict.hoangviet.firebase.ui.presenter.ChangePasswordPresenter;
import com.soict.hoangviet.firebase.ui.presenter.impl.ChangePasswordPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
class ChangePasswordActivityModule {
    @Provides
    internal fun provideChangePasswordInteractor(mInteractor: ChangePasswordInteractorImpl): ChangePasswordInteractor =
        mInteractor

    @Provides
    internal fun provideChangePasswordPresenter(mPresenter: ChangePasswordPresenterImpl): ChangePasswordPresenter =
        mPresenter
}
