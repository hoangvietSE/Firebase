package com.soict.hoangviet.firebase.ui.view.impl

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.adapter.*
import com.soict.hoangviet.firebase.data.network.response.ChatsResponse
import com.soict.hoangviet.firebase.ui.interactor.impl.MessageInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.MessagePresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.MessagePresenterImpl
import com.soict.hoangviet.firebase.ui.view.MessageView
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.fragment_friends.*

class MessageActivity : BaseActivity<MessagePresenter>(), MessageView, EndlessLoadingRecyclerViewAdapter.OnLoadingMoreListener,
        RecyclerViewAdapter.OnItemClickListener, BaseRecyclerView.BaseSwipeRefreshListener {
    companion object {
        const val EXTRA_USER_ID = "extra_user_id"
    }

    override val mLayoutRes: Int
        get() = R.layout.activity_message

    private var mMessageAdapter: MessageAdapter? = null
    private lateinit var receiver: String

    override fun getPresenter(): MessagePresenter {
        return MessagePresenterImpl(this, MessageInteractorImpl())
    }

    override fun initView() {
        initAdapter()
        getDataIntent()
        readMessage()
    }

    private fun initAdapter() {
        mMessageAdapter = MessageAdapter(this)
    }

    private fun readMessage() {
        mPresenter.readMessage(receiver)
    }

    private fun getDataIntent() {
        receiver = intent.getStringExtra(EXTRA_USER_ID)
    }

    override fun initListener() {
        btn_send.setOnClickListener {
            val msg: String = edt_message.text.toString()
            if (!TextUtils.isEmpty(msg)) {
                mPresenter.sendMessage(receiver, msg)
            }
        }
        toolbar.imvLeft?.setOnClickListener {
            finish()
        }
    }

    override fun addSender(mChatsResponse: ChatsResponse) {
        mMessageAdapter?.addModel(mChatsResponse, MessageAdapter.VIEW_TYPE_SENDER)
    }

    override fun addReceiver(mChatsResponse: ChatsResponse) {
        mMessageAdapter?.addModel(mChatsResponse, MessageAdapter.VIEW_TYPE_RECEIVER)
    }

    override fun clearMessage() {
        mMessageAdapter?.clear()
    }

    override fun onShowMessage() {
        recycler_view_message.setAdapter(mMessageAdapter!!)
        recycler_view_message.setLoadingMoreListner(this)
        recycler_view_message.setOnItemClickListener(this)
        recycler_view_message.setOnRefreshingListener(this)
        recycler_view_message.setLinearLayoutManagerMessage()
        recycler_view_message.disableRefreshing()
    }

    override fun onSendSuccess() {
        edt_message.setText("")
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    override fun onLoadMore() {
    }

    override fun onItemClick(parent: ViewGroup, viewType: Int, view: View, position: Int?) {
    }

    override fun onSwipeRefresh() {
    }
}