package com.soict.hoangviet.firebase.ui.view.impl

import android.graphics.BitmapFactory
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.soict.hoangviet.baseproject.extension.hideSoftKeyboard
import com.soict.hoangviet.baseproject.extension.loadImage
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.adapter.EmojiParentAdapter
import com.soict.hoangviet.firebase.adapter.MessageAdapter
import com.soict.hoangviet.firebase.adapter.RecyclerViewAdapter
import com.soict.hoangviet.firebase.data.local.Emoji
import com.soict.hoangviet.firebase.data.network.response.ChatsResponse
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.extension.visible
import com.soict.hoangviet.firebase.ui.presenter.MessagePresenter
import com.soict.hoangviet.firebase.ui.view.MessageView
import com.soict.hoangviet.firebase.utils.AppConstant
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.item_message_sender.view.*
import javax.inject.Inject


class MessageActivity : BaseActivity(), MessageView, RecyclerViewAdapter.OnItemClickListener {
    companion object {
        const val EXTRA_USER_ID = "extra_user_id"
        const val TOKEN_DEVICE_ID = "token_device_id"
    }

    override val mLayoutRes: Int
        get() = R.layout.activity_message

    @Inject
    lateinit var mPresenter: MessagePresenter
    private var mMessageAdapter: MessageAdapter? = null
    private var mEmojiParentAdapter: EmojiParentAdapter? = null
    private lateinit var mListEmoji: ArrayList<Emoji>
    private val receiver: String by lazy {
        intent.getStringExtra(EXTRA_USER_ID)
    }
    private val receiverToken: String by lazy {
        intent.getStringExtra(TOKEN_DEVICE_ID)
    }

    override fun initView() {
        mPresenter.onAttach(this)
        initAdapter()
        getDataIntent()
        readMessage()
        seenMessage()
        initEmojiAdapter()
    }

    private fun initEmojiAdapter() {
        mListEmoji = arrayListOf()
        assets.list("emoji").forEach {
            mListEmoji.add(
                Emoji(
                    it,
                    ArrayList(assets.list("emoji/" + it).asList())
                )
            )
        }
        mEmojiParentAdapter = EmojiParentAdapter(this)
        mEmojiParentAdapter?.addModels(mListEmoji, false)
        vp_emoji.adapter = mEmojiParentAdapter
        vp_emoji.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        // Set the icon and text for each tab
        TabLayoutMediator(tl_emoji, vp_emoji) { tab, position ->

        }.attach()
    }

    private fun seenMessage() {
        mPresenter.seenMessage(receiver)
    }

    private fun initAdapter() {
        mMessageAdapter = MessageAdapter(this)
    }

    private fun readMessage() {
        mPresenter.readMessage(receiver)
    }

    private fun getDataIntent() {
        mPresenter.showInfoUserMessage(receiver)
    }

    override fun initListener() {
        btn_send.setOnClickListener {
            val msg: String = edt_message.text.toString()
            if (!TextUtils.isEmpty(msg)) {
                mPresenter.sendMessage(receiver, msg, receiverToken)
            }
        }
        toolbar.imvLeft?.setOnClickListener {
            finish()
        }
        btn_emoj.setOnClickListener {

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

    override fun showInfoUserMessage(user: User) {
        when (user.status) {
            AppConstant.ONLINE -> {
                onOnlineMessage()
            }
            AppConstant.OFFLINE -> {
                onOfflineMessage()
            }
        }
        toolbar.showAvatar(user.avatar)
        toolbar.setMainName(user.fullname)
    }

    override fun onShowMessage() {
        recycler_view_message.setAdapter(mMessageAdapter!!)
        recycler_view_message.setOnRefreshListener {
        }
        recycler_view_message.setLoadingMoreListener {
        }
        recycler_view_message.setOnItemClickListener(this)
        recycler_view_message.setLinearLayoutManagerMessage()
        recycler_view_message.disableRefreshing()
    }

    override fun onSendSuccess() {
        edt_message.setText("")
    }

    override fun onOnlineMessage() {
        toolbar.showStatusOnline()
    }

    override fun onOfflineMessage() {
        toolbar.showStatusOffline()
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    override fun onItemClick(parent: ViewGroup, viewType: Int, view: View, position: Int?) {
        if (view.tv_seen.visibility == View.VISIBLE) {
            view.tv_seen.gone()
        } else if (view.tv_seen.visibility == View.GONE) {
            view.tv_seen.visible()
        }
    }

    override fun onStop() {
        super.onStop()
        mPresenter.onDetach()
        mPresenter.removeEventListenerSeenMessage()

    }
}