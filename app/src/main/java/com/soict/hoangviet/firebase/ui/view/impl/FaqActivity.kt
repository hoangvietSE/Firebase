package com.soict.hoangviet.firebase.ui.view.impl;

import com.soict.hoangviet.firebase.ui.presenter.FaqPresenter
import com.soict.hoangviet.firebase.ui.view.FaqView
import javax.inject.Inject

class FaqActivity : BaseWebView(), FaqView {
    @Inject
    lateinit var mPresenter: FaqPresenter
    override val webViewUrl: String
        get() = "https://developers.facebook.com/docs/messenger-platform/faq/"

    override fun initView() {
        mPresenter.onAttach(this)
        super.initView()
    }

    override fun initListener() {

    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }
}
