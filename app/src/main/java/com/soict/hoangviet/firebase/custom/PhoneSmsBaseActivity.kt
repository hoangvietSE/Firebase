package com.soict.hoangviet.firebase.custom

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import com.soict.hoangviet.firebase.data.network.request.RegisterRequest
import com.soict.hoangviet.firebase.ui.presenter.BasePresenter
import com.soict.hoangviet.firebase.ui.view.impl.BaseActivity

abstract class PhoneSmsBaseActivity<T : BasePresenter> : BaseActivity<T>(), TokenPhoneSms.SendTokenSmsListener,
    AuthenticateCallBack {

    protected var mTokenPhoneSms = TokenPhoneSms(this, this)
    protected var mUserPhoneNumber: String = ""
    protected var mCheckSendSmsSuccess : Boolean = false

    private fun initAuth(): FirebaseAuthenticate {
        return FirebaseAuthenticate(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mTokenPhoneSms.initDataPhoneSms()
        super.onCreate(savedInstanceState)
    }

    override fun onSendSmsSuccess() {
        mCheckSendSmsSuccess = true
    }

    override fun onSendSmsFailed() {
        mCheckSendSmsSuccess = false
    }

    protected fun register(registerRequest: RegisterRequest) {
        initAuth().register(registerRequest)
    }
}