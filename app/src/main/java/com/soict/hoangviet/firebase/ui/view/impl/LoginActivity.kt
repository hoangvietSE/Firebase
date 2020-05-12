package com.soict.hoangviet.firebase.ui.view.impl

import com.soict.hoangviet.baseproject.extension.launchActivity
import com.soict.hoangviet.baseproject.extension.onAvoidDoubleClick
import com.soict.hoangviet.baseproject.extension.toast
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.custom.FirebaseAuthBaseActivity
import com.soict.hoangviet.firebase.data.network.request.LoginRequest
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.ui.presenter.LoginPresenter
import com.soict.hoangviet.firebase.ui.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : FirebaseAuthBaseActivity(), LoginView {
    override val mLayoutRes: Int
        get() = R.layout.activity_login

    @Inject
    lateinit var mPresenter: LoginPresenter

    @Inject
    lateinit var mAppSharePreference: SharePreference

    override fun initView() {
        mPresenter.onAttach(this)
    }

    override fun initListener() {
        btn_login.onAvoidDoubleClick {
            val loginRequest = LoginRequest()
            loginRequest.email = edt_email.text.toString()
            loginRequest.password = edt_password.text.toString()
            mPresenter?.validateLogin(loginRequest)
        }
        tv_member.onAvoidDoubleClick {
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
        toast(resources.getString(R.string.login_success))
        hideLoading()
        mPresenter.saveCurrentUser()
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

    override fun goToMainScreen() {
        launchActivity<MainActivity>()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }
}