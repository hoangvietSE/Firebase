package com.soict.hoangviet.firebase.ui.view.impl

import com.soict.hoangviet.baseproject.extension.toast
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.custom.FirebaseAuthBaseActivity
import com.soict.hoangviet.firebase.data.network.request.LoginRequest
import com.soict.hoangviet.firebase.ui.interactor.impl.LoginInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.LoginPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.LoginPresenterImpl
import com.soict.hoangviet.firebase.ui.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edt_email
import kotlinx.android.synthetic.main.activity_login.edt_password


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
        tv_member.setOnClickListener {
            startActivity(RegisterActivity::class.java)
        }
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    override fun onAuthError() {
        hideLoading()
        toast(resources.getString(R.string.login_error))
    }

    override fun onAuthSuccess() {
        hideLoading()
        toast(resources.getString(R.string.login_success))
        mPresenter.saveCurrentUser()
        startActivityAndClearTask(MainActivity::class.java)
    }

    override fun onEmailEmpty() {
        toast(resources.getString(R.string.register_email_empty))
    }

    override fun onEmailError() {
        toast(resources.getString(R.string.register_email_error))
    }

    override fun onPasswordError() {
        toast(resources.getString(R.string.register_password_error))
    }

    override fun onPasswordEmpty() {
        toast(resources.getString(R.string.register_password_empty))
    }

    override fun onValidateSuccess(loginRequest: LoginRequest) {
        showLoading()
        login(loginRequest)
    }
}