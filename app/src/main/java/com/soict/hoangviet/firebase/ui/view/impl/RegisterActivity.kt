package com.soict.hoangviet.firebase.ui.view.impl

import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.custom.FirebaseAuthBaseActivity
import com.soict.hoangviet.firebase.data.network.request.RegisterRequest
import com.soict.hoangviet.firebase.ui.interactor.impl.RegisterInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.RegisterPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.RegisterPresenterImpl
import com.soict.hoangviet.firebase.ui.view.RegisterView
import com.soict.hoangviet.firebase.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : FirebaseAuthBaseActivity<RegisterPresenter>(), RegisterView {
    override val mLayoutRes: Int
        get() = R.layout.activity_register

    override fun getPresenter(): RegisterPresenter {
        return RegisterPresenterImpl(this, RegisterInteractorImpl())
    }

    override fun initView() {

    }

    override fun initListener() {
        btn_register.setOnClickListener {
            val registerRegisterActivity = RegisterRequest()
            registerRegisterActivity.email = edt_email.text.toString()
            registerRegisterActivity.username = edt_username.text.toString()
            registerRegisterActivity.password = edt_password.text.toString()
            mPresenter.validateRegister(registerRegisterActivity)
        }
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    override fun onAuthError() {
        hideLoading()
        ToastUtil.show(resources.getString(R.string.register_error))
    }

    override fun onAuthSuccess() {
        hideLoading()
        ToastUtil.show(resources.getString(R.string.register_success))
        startActivityAndClearTask(LoginActivity::class.java)
    }

    override fun onUsernameEmpty() {
        til_username.error = resources.getString(R.string.register_username_empty)
        edt_username.requestFocus()
    }

    override fun onUsernameError() {
        til_username.error = resources.getString(R.string.register_username_empty)
        edt_username.requestFocus()
    }

    override fun onEmailEmpty() {
        til_email.error = resources.getString(R.string.register_email_empty)
        edt_email.requestFocus()
    }

    override fun onEmailError() {
        til_email.error = resources.getString(R.string.register_email_error)
        edt_email.requestFocus()
    }

    override fun onPasswordError() {
        til_password.error = resources.getString(R.string.register_password_error)
        til_password.requestFocus()
    }

    override fun onPasswordEmpty() {
        til_password.error = resources.getString(R.string.register_password_empty)
        til_password.requestFocus()
    }

    override fun onValidateSuccess(registerRequest: RegisterRequest) {
        showLoading()
        register(registerRequest)
    }
}