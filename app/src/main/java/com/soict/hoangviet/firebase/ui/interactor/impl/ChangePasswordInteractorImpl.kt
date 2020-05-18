package com.soict.hoangviet.firebase.ui.interactor.impl;

import javax.inject.Inject;
import com.soict.hoangviet.firebase.ui.interactor.ChangePasswordInteractor;
import com.soict.hoangviet.firebase.data.network.api.Repository;


class ChangePasswordInteractorImpl @Inject internal constructor(
    mRepository: Repository
) : BaseInteractorImpl(repository = mRepository), ChangePasswordInteractor {

}