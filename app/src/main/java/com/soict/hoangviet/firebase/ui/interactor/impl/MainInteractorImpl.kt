package com.soict.hoangviet.firebase.ui.interactor.impl

import com.soict.hoangviet.baseproject.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.ui.interactor.MainInteractor
import javax.inject.Inject

class MainInteractorImpl
@Inject internal constructor(sharePreference: SharePreference) :
    BaseInteractorImpl(mAppSharePreference = sharePreference), MainInteractor {

}