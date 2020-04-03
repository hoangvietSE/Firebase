package com.soict.hoangviet.firebase.ui.interactor.impl

import com.soict.hoangviet.firebase.data.network.api.Repository
import com.soict.hoangviet.firebase.ui.interactor.RegisterInteractor
import com.soict.hoangviet.firebase.ui.interactor.SplashInteractor
import javax.inject.Inject

class SplashInteractorImpl @Inject internal constructor(
    mRepository: Repository
): BaseInteractorImpl(repository = mRepository), SplashInteractor {
}