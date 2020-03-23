package com.soict.hoangviet.firebase.ui.module

import com.soict.hoangviet.firebase.ui.interactor.UpdateProfileInteractor
import com.soict.hoangviet.firebase.ui.interactor.impl.UpdateProfileInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.UpdateProfilePresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.UpdateProfilePresenterImpl
import dagger.Module
import dagger.Provides

@Module
class UpdateProfileActivityModule {
    @Provides
    internal fun provideUpdateProfileInteractor(updateProfileInteractorImpl: UpdateProfileInteractorImpl)
            : UpdateProfileInteractor = updateProfileInteractorImpl

    @Provides
    internal fun provideUpdateProfilePresenter(updateProfilePresenterImpl: UpdateProfilePresenterImpl)
            : UpdateProfilePresenter = updateProfilePresenterImpl

}