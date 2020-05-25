package com.soict.hoangviet.firebase.ui.presenter.impl

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.soict.hoangviet.firebase.builder.DatabaseFirebase
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.ui.interactor.MainInteractor
import com.soict.hoangviet.firebase.ui.presenter.MainPresenter
import com.soict.hoangviet.firebase.ui.view.MainView
import com.soict.hoangviet.firebase.utils.AppConstant
import javax.inject.Inject

class MainPresenterImpl @Inject internal constructor(
    mainInteractor: MainInteractor,
    sharePreference: SharePreference
) :
    BasePresenterImpl<MainView, MainInteractor>(
        mInteractor = mainInteractor,
        mAppSharePreference = sharePreference
    ), MainPresenter {
    lateinit var pairUser: Pair<DatabaseReference, ValueEventListener>
    override fun onAttach(view: MainView?) {
        super.onAttach(view)
        pairUser  = DatabaseFirebase.Builder()
            .reference("Users")
            .child(currentId!!)
            .build()
    }

    override fun setStatus(status: Int) {
        pairUser.first.updateChildren(mapOf(AppConstant.DataBaseRef.STATUS to status))
    }

    override fun clearDeviceToken() {
        pairUser.first.updateChildren(mapOf(AppConstant.DataBaseRef.DEVICE_TOKEN to ""))
    }

    override fun onDetach() {
        super.onDetach()
        pairUser.first.removeEventListener(pairUser.second)
    }
}