package com.soict.hoangviet.firebase.ui.module

import com.soict.hoangviet.firebase.ui.interactor.ProfileInteractor
import com.soict.hoangviet.firebase.ui.interactor.impl.ProfileInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.ProfilePresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.ProfilePresenterImpl
import dagger.Module
import dagger.Provides

@Module
class ProfileFragmentModule {
    @Provides
    internal fun provideProfileInteractor(profileInteractorImpl: ProfileInteractorImpl)
            : ProfileInteractor = profileInteractorImpl

    @Provides
    internal fun provideProfilePresenter(profilePresenterImpl: ProfilePresenterImpl)
            : ProfilePresenter = profilePresenterImpl

}