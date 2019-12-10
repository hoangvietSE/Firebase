package com.soict.hoangviet.firebase.ui.view.impl

import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.custom.FirebaseAuthBaseActivity
import com.soict.hoangviet.firebase.data.network.request.LoginRequest
import com.soict.hoangviet.firebase.data.network.request.RegisterRequest
import com.soict.hoangviet.firebase.ui.interactor.impl.LoginInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.LoginPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.LoginPresenterImpl
import com.soict.hoangviet.firebase.ui.view.LoginView
import com.soict.hoangviet.firebase.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edt_password
import kotlinx.android.synthetic.main.activity_login.til_email
import kotlinx.android.synthetic.main.activity_login.til_password

class LoginActivity : FirebaseAuthBaseActivity<LoginPresenter>(), LoginView {
    override val mLayoutRes: Int
        get() = R.layout.activity_login

    override fun getPresenter(): LoginPresenter {
        return LoginPresenterImpl(this, LoginInteractorImpl())
    }

    override fun initView() {
    }

    override fun initListener() {
        btn_login.setOnClickListener {
            val loginRequest = LoginRequest()
            loginRequest.email = edt_email.text.toString()
            loginRequest.password = edt_password.text.toString()
            mPresenter?.validateLogin(loginRequest)
        }
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    override fun onAuthError() {
        hideLoading()
        ToastUtil.show(resources.getString(R.string.login_error))
    }

    override fun onAuthSuccess() {
        hideLoading()
        ToastUtil.show(resources.getString(R.string.login_success))
    }

    override fun onEmailEmpty() {
        defaultTextInputLayout()
        til_email.error = resources.getString(R.string.register_email_empty)
        edt_email.requestFocus()
    }

    override fun onEmailError() {
        defaultTextInputLayout()
        til_email.error = resources.getString(R.string.register_email_error)
        edt_email.requestFocus()
    }

    override fun onPasswordError() {
        defaultTextInputLayout()
        til_password.error = resources.getString(R.string.register_password_error)
        til_password.requestFocus()
    }

    override fun onPasswordEmpty() {
        defaultTextInputLayout()
        til_password.error = resources.getString(R.string.register_password_empty)
        til_password.requestFocus()
    }

    override fun onValidateSuccess(loginRequest: LoginRequest) {
        showLoading()
        login(loginRequest)
    }

    private fun defaultTextInputLayout() {
        til_email.error = null
        til_password.error = null
    }
}