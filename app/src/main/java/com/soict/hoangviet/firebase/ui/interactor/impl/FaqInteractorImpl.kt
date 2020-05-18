package com.soict.hoangviet.firebase.ui.interactor.impl;

import javax.inject.Inject;
import com.soict.hoangviet.firebase.ui.interactor.FaqInteractor;
import com.soict.hoangviet.firebase.data.network.api.Repository;


class FaqInteractorImpl @Inject internal constructor(
    mRepository: Repository
) : BaseInteractorImpl(repository = mRepository), FaqInteractor {

}