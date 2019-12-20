package com.soict.hoangviet.firebase.ui.view.impl

import android.content.Intent
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.data.network.request.RegisterRequest
import com.soict.hoangviet.firebase.ui.interactor.impl.RegisterInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.RegisterPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.RegisterPresenterImpl
import com.soict.hoangviet.firebase.ui.view.RegisterView
import com.soict.hoangviet.firebase.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_login.edt_email
import kotlinx.android.synthetic.main.activity_login.edt_password
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : BaseActivity<RegisterPresenter>(), RegisterView {
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
            registerRegisterActivity.fullName = edt_fullname.text.toString()
            registerRegisterActivity.phoneNumber = edt_phone.text.toString()
            registerRegisterActivity.password = edt_password.text.toString()
            mPresenter.validateRegister(registerRegisterActivity)
        }
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

//    override fun onAuthError() {
//        hideLoading()
//        ToastUtil.show(resources.getString(R.string.register_error))
//    }
//
//    override fun onAuthSuccess() {
//        hideLoading()
//        ToastUtil.show(resources.getString(R.string.register_success))
//        startActivityAndClearTask(LoginActivity::class.java)
//    }

    override fun onFullNameEmpty() {
        ToastUtil.show(resources.getString(R.string.register_fullname_empty))
    }

    override fun onPhoneEmpty() {
        ToastUtil.show(resources.getString(R.string.register_phone_empty))
    }

    override fun onPhoneError() {
        ToastUtil.show(resources.getString(R.string.register_phone_error))
    }

    override fun onEmailEmpty() {
        ToastUtil.show(resources.getString(R.string.register_email_empty))
    }

    override fun onEmailError() {
        ToastUtil.show(resources.getString(R.string.register_email_error))
    }

    override fun onPasswordError() {
        ToastUtil.show(resources.getString(R.string.register_password_error))
    }

    override fun onPasswordEmpty() {
        ToastUtil.show(resources.getString(R.string.register_password_empty))
    }

    override fun onValidateSuccess(registerRequest: RegisterRequest) {
        startActivity(Intent(this, ConfirmActivity::class.java).apply {
            putExtra(ConfirmActivity.EXTRA_REGISTER_OBJECT, registerRequest)
        })
    }
}