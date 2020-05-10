package com.soict.hoangviet.firebase.ui.interactor.impl;

import javax.inject.Inject;
import com.soict.hoangviet.firebase.ui.interactor.TutorialInteractor;
import com.soict.hoangviet.firebase.data.network.api.Repository;


class TutorialInteractorImpl @Inject internal constructor(
    mRepository: Repository
) : BaseInteractorImpl(repository = mRepository), TutorialInteractor {

}