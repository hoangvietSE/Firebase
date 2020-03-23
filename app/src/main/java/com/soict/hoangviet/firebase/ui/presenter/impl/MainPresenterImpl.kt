package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.baseproject.data.sharepreference.AppSharePreference
import com.soict.hoangviet.baseproject.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.application.BaseApplication
import com.soict.hoangviet.firebase.ui.interactor.MainInteractor
import com.soict.hoangviet.firebase.ui.presenter.MainPresenter
import com.soict.hoangviet.firebase.ui.view.MainView
import javax.inject.Inject

class MainPresenterImpl @Inject internal constructor(
    mainInteractor: MainInteractor,
    sharePreference: SharePreference
) :
    BasePresenterImpl<MainView, MainInteractor>(mInteractor = mainInteractor, mAppSharePreference = sharePreference), MainPresenter {
    override fun setStatus(status: Int) {
        val ref = datebaseRef.getReference("Users")
            .child(currentId!!)
        val userRecored: MutableMap<String, Any> = mutableMapOf()
        userRecored.put("status", status)
        ref.updateChildren(userRecored)
    }
}