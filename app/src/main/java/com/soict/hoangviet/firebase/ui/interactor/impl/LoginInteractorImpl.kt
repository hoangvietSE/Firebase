package com.soict.hoangviet.firebase.ui.interactor.impl

import com.soict.hoangviet.firebase.data.network.api.Repository
import com.soict.hoangviet.firebase.ui.interactor.HomeInteractor
import com.soict.hoangviet.firebase.ui.interactor.LoginInteractor
import javax.inject.Inject

class LoginInteractorImpl @Inject internal constructor(
    mRepository: Repository
): BaseInteractorImpl(repository = mRepository), LoginInteractor {
}