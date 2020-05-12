package com.soict.hoangviet.firebase.ui.interactor.impl;

import javax.inject.Inject;
import com.soict.hoangviet.firebase.ui.interactor.NotificationInteractor;
import com.soict.hoangviet.firebase.data.network.api.Repository;


class NotificationInteractorImpl @Inject internal constructor(
    mRepository: Repository
) : BaseInteractorImpl(repository = mRepository), NotificationInteractor {

}