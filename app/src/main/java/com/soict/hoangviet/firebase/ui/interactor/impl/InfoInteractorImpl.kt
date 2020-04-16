package com.soict.hoangviet.firebase.ui.interactor.impl;

import com.soict.hoangviet.firebase.data.network.api.Repository
import javax.inject.Inject;
import com.soict.hoangviet.firebase.ui.interactor.InfoInteractor;

class InfoInteractorImpl @Inject internal constructor(
    mRepository: Repository
) : BaseInteractorImpl(repository = mRepository), InfoInteractor {

}