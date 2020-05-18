package com.soict.hoangviet.firebase.ui.module;

import com.soict.hoangviet.firebase.ui.interactor.FaqInteractor;
import com.soict.hoangviet.firebase.ui.interactor.impl.FaqInteractorImpl;
import com.soict.hoangviet.firebase.ui.presenter.FaqPresenter;
import com.soict.hoangviet.firebase.ui.presenter.impl.FaqPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
class FaqActivityModule {
    @Provides
    internal fun provideFaqInteractor(mInteractor: FaqInteractorImpl): FaqInteractor = mInteractor

    @Provides
    internal fun provideFaqPresenter(mPresenter: FaqPresenterImpl): FaqPresenter = mPresenter
}
