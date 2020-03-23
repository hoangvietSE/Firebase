package com.soict.hoangviet.firebase.ui.module

import com.soict.hoangviet.firebase.ui.interactor.FriendsInteractor
import com.soict.hoangviet.firebase.ui.interactor.impl.FriendsInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.FriendsPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.FriendsPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class FriendsFragmentModule {
    @Provides
    internal fun provideFriendsInteractor(friendsInteractorImpl: FriendsInteractorImpl)
            : FriendsInteractor = friendsInteractorImpl

    @Provides
    internal fun provideFriendsPresenter(friendsPresenterImpl: FriendsPresenterImpl)
            : FriendsPresenter = friendsPresenterImpl

}