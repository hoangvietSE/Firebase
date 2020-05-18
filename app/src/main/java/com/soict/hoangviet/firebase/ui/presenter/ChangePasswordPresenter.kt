package com.soict.hoangviet.firebase.ui.presenter;

import com.soict.hoangviet.firebase.ui.view.ChangePasswordView;
import com.soict.hoangviet.firebase.ui.interactor.ChangePasswordInteractor;

interface ChangePasswordPresenter : BasePresenter<ChangePasswordView, ChangePasswordInteractor> {
    fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String)
}