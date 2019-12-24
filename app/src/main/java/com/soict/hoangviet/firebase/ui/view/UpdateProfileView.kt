package com.soict.hoangviet.firebase.ui.view

interface UpdateProfileView : BaseView {
    fun onFullNameEmpty()
    fun onBirthdayEmpty()
    fun onAvatarUploadError()
    fun uploadSuccess()
    fun uploadError()
}