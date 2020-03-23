package com.soict.hoangviet.firebase.ui.module

import com.soict.hoangviet.firebase.ui.interactor.DynamicLinkInteractor
import com.soict.hoangviet.firebase.ui.interactor.impl.DynamicLinkInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.DynamicLinkPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.DynamicLinkPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class DynamicLinkActivityModule {
    @Provides
    internal fun provideDynamicLinkInteractor(dynamicLinkInteractor: DynamicLinkInteractorImpl)
            : DynamicLinkInteractor = dynamicLinkInteractor

    @Provides
    internal fun provideDynamicLinkPresenter(dynamicLinkPresenterImpl: DynamicLinkPresenterImpl)
            : DynamicLinkPresenter = dynamicLinkPresenterImpl
}