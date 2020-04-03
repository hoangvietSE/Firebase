package com.soict.hoangviet.firebase.ui.interactor.impl

import com.soict.hoangviet.firebase.data.network.api.Repository
import com.soict.hoangviet.firebase.ui.interactor.SplashInteractor
import com.soict.hoangviet.firebase.ui.interactor.UpdateProfileInteractor
import javax.inject.Inject

class UpdateProfileInteractorImpl @Inject internal constructor(
    mRepository: Repository
): BaseInteractorImpl(repository = mRepository), UpdateProfileInteractor {
}