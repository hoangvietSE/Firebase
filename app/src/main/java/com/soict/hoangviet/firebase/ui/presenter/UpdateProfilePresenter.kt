package com.soict.hoangviet.firebase.ui.presenter

import android.net.Uri
import com.soict.hoangviet.firebase.data.network.request.UpdateProfileRequest

interface UpdateProfilePresenter : BasePresenter {
    fun updateProfile(mUpdateProfileRequest: UpdateProfileRequest)
    fun setAvatar(uriImage: Uri)
    fun setGender(position: Int)
}