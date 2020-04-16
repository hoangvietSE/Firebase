package com.soict.hoangviet.firebase.ui.view.impl;

import com.soict.hoangviet.firebase.R;
import com.soict.hoangviet.firebase.ui.view.InfoView
import com.soict.hoangviet.firebase.ui.presenter.InfoPresenter
import javax.inject.Inject

class InfoActivity : BaseActivity(), InfoView {

    override val mLayoutRes: Int
        get() = R.layout.activity_info

    @Inject
    lateinit var mPresenter: InfoPresenter

    override fun initView() {
        mPresenter.onAttach(this)
    }

    override fun initListener() {

    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }
}
