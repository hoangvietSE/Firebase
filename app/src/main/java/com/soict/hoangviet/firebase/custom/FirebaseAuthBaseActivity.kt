package com.soict.hoangviet.firebase.custom

import com.google.firebase.auth.FirebaseAuth
import com.soict.hoangviet.firebase.data.network.request.LoginRequest
import com.soict.hoangviet.firebase.data.network.request.RegisterRequest
import com.soict.hoangviet.firebase.ui.presenter.BasePresenter
import com.soict.hoangviet.firebase.ui.view.impl.BaseActivity

abstract class FirebaseAuthBaseActivity<T : BasePresenter> : BaseActivity<T>(), AuthenticateCallBack {
    override fun initView() {
    }

    private fun initAuth(): FirebaseAuthenticate {
        return FirebaseAuthenticate(this)
    }

    override fun initListener() {

    }

    protected fun register(registerRequest: RegisterRequest) {
        initAuth().register(registerRequest)
    }

    protected fun login(loginRequest: LoginRequest) {
        initAuth().login(loginRequest)
    }

    protected fun logout(){
        initAuth().logout()
    }



}