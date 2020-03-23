package com.soict.hoangviet.firebase.ui.presenter

import android.net.Uri
import com.soict.hoangviet.firebase.data.network.request.UpdateProfileRequest
import com.soict.hoangviet.firebase.ui.interactor.UpdateProfileInteractor
import com.soict.hoangviet.firebase.ui.view.UpdateProfileView

interface UpdateProfilePresenter : BasePresenter<UpdateProfileView, UpdateProfileInteractor> {
    fun updateProfile(mUpdateProfileRequest: UpdateProfileRequest)
    fun setAvatar(uriImage: Uri)
    fun setGender(position: Int)
}