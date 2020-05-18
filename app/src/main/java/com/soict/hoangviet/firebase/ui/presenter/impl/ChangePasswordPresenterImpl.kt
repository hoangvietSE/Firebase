package com.soict.hoangviet.firebase.ui.presenter.impl;

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference;
import com.soict.hoangviet.firebase.extension.isValidatePassword
import com.soict.hoangviet.firebase.ui.presenter.ChangePasswordPresenter;
import com.soict.hoangviet.firebase.ui.view.ChangePasswordView;
import com.soict.hoangviet.firebase.ui.interactor.ChangePasswordInteractor;
import com.soict.hoangviet.firebase.utils.AppConstant

import javax.inject.Inject;

class ChangePasswordPresenterImpl @Inject internal constructor(
    interactor: ChangePasswordInteractor,
    sharePreference: SharePreference
) :
    BasePresenterImpl<ChangePasswordView, ChangePasswordInteractor>(
        mInteractor = interactor,
        mAppSharePreference = sharePreference
    ), ChangePasswordPresenter {
    override fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String) {
        when {
            oldPassword.isNullOrEmpty() -> mView?.showEmptyOldPassword()
            !oldPassword.isValidatePassword() -> mView?.showErrorOldPassword()
            newPassword.isNullOrEmpty() -> mView?.showEmptyNewPassword()
            !newPassword.isValidatePassword() -> mView?.showErrorNewPassword()
            confirmPassword.isNullOrEmpty() -> mView?.showEmptyConfirmPassword()
            newPassword != confirmPassword -> mView?.showErrorConfirmPassword()
            else -> changePasswordFirebase(oldPassword, newPassword, confirmPassword)
        }
    }

    private fun changePasswordFirebase(
        oldPassword: String,
        newPassword: String,
        confirmPassword: String
    ) {
        FirebaseAuth.getInstance().currentUser?.reauthenticate(
            EmailAuthProvider
                .getCredential(
                    mAppSharePreference.get(
                        AppConstant.SharePreference.USER,
                        User::class.java
                    ).email, oldPassword
                )
        )
            ?.addOnCompleteListener {
                when {
                    it.isSuccessful -> {
                        FirebaseAuth.getInstance().currentUser?.updatePassword(newPassword)
                            ?.addOnCompleteListener {
                                when {
                                    it.isSuccessful -> mView?.showSuccessfulChangePassword()
                                    else -> mView?.showErrorChangePassword()
                                }
                            }
                    }
                    else -> mView?.showErrorChangePassword()
                }
            }
    }

}