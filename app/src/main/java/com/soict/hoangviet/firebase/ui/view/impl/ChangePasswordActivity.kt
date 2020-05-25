package com.soict.hoangviet.firebase.ui.view.impl;

import com.soict.hoangviet.baseproject.extension.addTextChangeListener
import com.soict.hoangviet.baseproject.extension.onAvoidDoubleClick
import com.soict.hoangviet.baseproject.extension.toast
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.ui.presenter.ChangePasswordPresenter
import com.soict.hoangviet.firebase.ui.view.ChangePasswordView
import kotlinx.android.synthetic.main.activity_change_password.*
import javax.inject.Inject

class ChangePasswordActivity : BaseActivity(), ChangePasswordView {

    override val mLayoutRes: Int
        get() = R.layout.activity_change_password

    @Inject
    lateinit var mPresenter: ChangePasswordPresenter

    override fun showErrorOldPassword() {

        input_password_old.error = getString(R.string.change_password_old_error_type)
    }

    override fun showEmptyOldPassword() {
        input_password_old.error = getString(R.string.change_password_old_no_type)
    }

    override fun showErrorNewPassword() {
        input_password_new.error = getString(R.string.change_password_new_error_type)
    }

    override fun showEmptyNewPassword() {
        input_password_new.error = getString(R.string.change_password_new_no_type)
    }

    override fun showEmptyConfirmPassword() {
        input_password_confirm.error = getString(R.string.change_password_retype_no_type)
    }

    override fun showErrorConfirmPassword() {
        input_password_confirm.error = getString(R.string.change_password_not_exactly)
    }

    override fun showErrorChangePassword() {
        hideLoading()
        toast(getString(R.string.change_password_failed))
        et_change_password_old.setText("")
        et_change_password_new.setText("")
        et_change_password_retype.setText("")
    }

    override fun showSuccessfulChangePassword() {
        hideLoading()
        toast(getString(R.string.change_password_success))
        finish()
    }

    override fun initView() {
        mPresenter.onAttach(this)
    }

    override fun initListener() {
        Pair(input_password_old, et_change_password_old).addTextChangeListener()
        Pair(input_password_new, et_change_password_new).addTextChangeListener()
        Pair(input_password_confirm, et_change_password_retype).addTextChangeListener()
        toolbar.setOnBackClickListener {
            finish()
        }
        btn_save.onAvoidDoubleClick {
            mPresenter.changePassword(
                et_change_password_old.text.toString(),
                et_change_password_new.text.toString(),
                et_change_password_retype.text.toString()
            )
        }
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }
}
