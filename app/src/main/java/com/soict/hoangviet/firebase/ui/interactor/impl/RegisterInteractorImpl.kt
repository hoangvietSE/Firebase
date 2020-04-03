package com.soict.hoangviet.firebase.ui.interactor.impl

import com.soict.hoangviet.firebase.data.network.api.Repository
import com.soict.hoangviet.firebase.ui.interactor.ProfileInteractor
import com.soict.hoangviet.firebase.ui.interactor.RegisterInteractor
import javax.inject.Inject

class RegisterInteractorImpl @Inject internal constructor(
    mRepository: Repository
): BaseInteractorImpl(repository = mRepository), RegisterInteractor {
}