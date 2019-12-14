package com.soict.hoangviet.firebase.ui.view.impl

import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.interactor.impl.MessageInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.MessagePresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.MessagePresenterImpl
import com.soict.hoangviet.firebase.ui.view.MessageView
import com.soict.hoangviet.firebase.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : BaseActivity<MessagePresenter>(), MessageView {
    companion object {
        const val EXTRA_USER_ID = "extra_user_id"
    }

    override val mLayoutRes: Int
        get() = R.layout.activity_message

    override fun getPresenter(): MessagePresenter {
        return MessagePresenterImpl(this, MessageInteractorImpl())
    }

    override fun initView() {
        getDataIntent()
    }

    private fun getDataIntent() {
        val userId = intent.getStringExtra(EXTRA_USER_ID)
        getCurrentUser(userId)
    }

    private fun getCurrentUser(userId: String?) {
        getCurrentUser(userId!!, object : RealTimeDatabaseListener<User> {
            override fun onLoadSuccess(data: User) {
                ToastUtil.show("Chat with ${data.username}")
                toolbar.setTitle(data.username)
            }

            override fun onLoadError() {

            }
        })
    }

    override fun initListener() {
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }
}