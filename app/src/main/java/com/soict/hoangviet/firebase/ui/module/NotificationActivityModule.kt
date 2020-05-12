package com.soict.hoangviet.firebase.ui.module;

import com.soict.hoangviet.firebase.ui.interactor.NotificationInteractor;
import com.soict.hoangviet.firebase.ui.interactor.impl.NotificationInteractorImpl;
import com.soict.hoangviet.firebase.ui.presenter.NotificationPresenter;
import com.soict.hoangviet.firebase.ui.presenter.impl.NotificationPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
class NotificationActivityModule {
    @Provides
    internal fun provideNotificationInteractor(mInteractor: NotificationInteractorImpl): NotificationInteractor =
        mInteractor

    @Provides
    internal fun provideNotificationPresenter(mPresenter: NotificationPresenterImpl): NotificationPresenter =
        mPresenter
}
