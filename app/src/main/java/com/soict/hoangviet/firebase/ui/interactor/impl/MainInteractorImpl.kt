package com.soict.hoangviet.firebase.ui.interactor.impl

import com.soict.hoangviet.firebase.data.network.api.Repository
import com.soict.hoangviet.firebase.ui.interactor.LoginInteractor
import com.soict.hoangviet.firebase.ui.interactor.MainInteractor
import javax.inject.Inject

class MainInteractorImpl @Inject internal constructor(
    mRepository: Repository
): BaseInteractorImpl(repository = mRepository), MainInteractor {
}