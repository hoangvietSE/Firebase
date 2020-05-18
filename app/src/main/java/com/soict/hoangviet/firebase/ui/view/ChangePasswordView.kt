package com.soict.hoangviet.firebase.ui.view;

interface ChangePasswordView : BaseView {
    fun showErrorOldPassword()
    fun showEmptyOldPassword()
    fun showErrorNewPassword()
    fun showEmptyNewPassword()
    fun showEmptyConfirmPassword()
    fun showErrorConfirmPassword()
    fun showErrorChangePassword()
    fun showSuccessfulChangePassword()
}