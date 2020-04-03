package com.soict.hoangviet.firebase.ui.interactor.impl

import com.soict.hoangviet.firebase.data.network.ICallBack
import com.soict.hoangviet.firebase.data.network.api.BaseRetrofit
import com.soict.hoangviet.firebase.data.network.api.Repository
import com.soict.hoangviet.firebase.data.network.response.BaseListEntityResponse
import com.soict.hoangviet.firebase.data.network.response.TestResponse
import com.soict.hoangviet.firebase.ui.interactor.MessageInteractor
import com.soict.hoangviet.firebase.ui.interactor.ProfileInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileInteractorImpl @Inject internal constructor(
    mRepository: Repository
): BaseInteractorImpl(repository = mRepository), ProfileInteractor {
}