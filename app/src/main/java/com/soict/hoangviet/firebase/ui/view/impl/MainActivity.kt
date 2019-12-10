package com.soict.hoangviet.firebase.ui.view.impl

import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.ui.interactor.impl.MainInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.MainPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.MainPresenterImpl
import com.soict.hoangviet.firebase.ui.view.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenter>(), MainView {
    override val mLayoutRes: Int
        get() = R.layout.activity_main

    override fun getPresenter(): MainPresenter {
        return MainPresenterImpl(this, MainInteractorImpl())
    }

    override fun initView() {
    }

    override fun initListener() {
        btn_login.setOnClickListener {
            startActivity(LoginActivity::class.java)
        }
        btn_register.setOnClickListener {
            startActivity(RegisterActivity::class.java)
        }
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }
}