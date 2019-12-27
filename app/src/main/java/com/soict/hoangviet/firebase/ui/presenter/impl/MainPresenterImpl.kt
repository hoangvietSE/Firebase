package com.soict.hoangviet.firebase.ui.presenter.impl

import com.soict.hoangviet.firebase.application.BaseApplication
import com.soict.hoangviet.firebase.data.sharepreference.AppSharePreference
import com.soict.hoangviet.firebase.ui.interactor.MainInteractor
import com.soict.hoangviet.firebase.ui.presenter.MainPresenter
import com.soict.hoangviet.firebase.ui.view.MainView

class MainPresenterImpl(mView: MainView, mInteractor: MainInteractor) :
    BasePresenterImpl<MainView, MainInteractor>(mView, mInteractor), MainPresenter {
    override fun setStatus(status: Int) {
        val ref = datebaseRef.getReference("Users")
            .child(AppSharePreference.getInstance(BaseApplication.instance).getUser()!!.id)
        val userRecored: MutableMap<String, Any> = mutableMapOf()
        userRecored.put("status", status)
        ref.updateChildren(userRecored)
    }
}