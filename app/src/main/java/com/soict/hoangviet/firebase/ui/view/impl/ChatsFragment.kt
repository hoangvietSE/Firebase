package com.soict.hoangviet.firebase.ui.view.impl

import android.os.Bundle
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.ui.interactor.impl.ChatsInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.ChatsPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.ChatsPresenterImpl
import com.soict.hoangviet.firebase.ui.view.ChatsView

class ChatsFragment : BaseFragment<ChatsPresenter>(), ChatsView {
    override val mLayoutRes: Int
        get() = R.layout.fragment_chats

    companion object {
        fun getInstance(): ChatsFragment {
            val chatsFragment = ChatsFragment()
            val bundle = Bundle()
            chatsFragment.arguments = bundle
            return chatsFragment
        }
    }

    override fun getPresenter(): ChatsPresenter {
        return ChatsPresenterImpl(this, ChatsInteractorImpl())
    }

    override fun initView() {

    }

    override fun initListener() {

    }
}