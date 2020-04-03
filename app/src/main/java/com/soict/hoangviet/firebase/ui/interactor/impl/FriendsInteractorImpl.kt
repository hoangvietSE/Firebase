package com.soict.hoangviet.firebase.ui.interactor.impl

import com.soict.hoangviet.firebase.data.network.api.Repository
import com.soict.hoangviet.firebase.ui.interactor.ConfirmInteractor
import com.soict.hoangviet.firebase.ui.interactor.FriendsInteractor
import javax.inject.Inject

class FriendsInteractorImpl @Inject internal constructor(
    mRepository: Repository
): BaseInteractorImpl(repository = mRepository), FriendsInteractor {
}

