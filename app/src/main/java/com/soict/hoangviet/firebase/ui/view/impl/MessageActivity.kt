package com.soict.hoangviet.firebase.ui.view.impl

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.soict.hoangviet.baseproject.extension.hideSoftKeyboard
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.adapter.EmojiParentAdapter
import com.soict.hoangviet.firebase.adapter.MessageAdapter
import com.soict.hoangviet.firebase.common.BasePhotoActivity
import com.soict.hoangviet.firebase.data.local.Emoji
import com.soict.hoangviet.firebase.data.network.response.ChatsResponse
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.extension.visible
import com.soict.hoangviet.firebase.ui.presenter.MessagePresenter
import com.soict.hoangviet.firebase.ui.view.MessageView
import com.soict.hoangviet.firebase.utils.AppConstant
import kotlinx.android.synthetic.main.activity_message.*
import javax.inject.Inject


class MessageActivity : BasePhotoActivity(), MessageView {
    companion object {
        const val EXTRA_USER_ID = "extra_user_id"
        const val TOKEN_DEVICE_ID = "token_device_id"
        const val FACE_EMOJI = 0
        const val FISH_EMOJI = 1
        const val KITTY_EMOJI = 2
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
        mEmojiParentAdapter?.onItemEmojiClick = { root, nameIcon ->
            mPresenter.sendMessage(
                receiver,
                "emoji/${root}/${nameIcon}",
                receiverToken,
                AppConstant.TypeMessage.EMOJI
            )
        }
        mEmojiParentAdapter?.addModels(mListEmoji, false)
        vp_emoji.adapter = mEmojiParentAdapter
        vp_emoji.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        // Set the icon and text for each tab
        TabLayoutMediator(tl_emoji, vp_emoji) { tab, position ->
            when (position) {
                FACE_EMOJI -> {
                    setIconTabLayout(tab, "emoji/face_emoji/angel.png")
                }
                FISH_EMOJI -> {
                    setIconTabLayout(tab, "emoji/fish_emoji/crab.png")
                }
                KITTY_EMOJI -> {
                    setIconTabLayout(tab, "emoji/kitty_emoji/idea.png")
                }
            }
        }.attach()
    }

    fun setIconTabLayout(tab: TabLayout.Tab, stringFile: String) {
        tab.icon = Drawable.createFromStream(
            assets.open(stringFile),
            null
        )
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
        edt_message.setOnTouchListener { v, event ->
            if (ll_emoji.isShown) ll_emoji.gone()
            false
        }
        btn_emoj.setOnClickListener {
            hideSoftKeyboard()
            Handler().postDelayed({
                when {
                    ll_emoji.isShown -> ll_emoji.gone()
                    else -> ll_emoji.visible()
                }
            }, 300)
        }
        btn_send_image_capture.setOnClickListener {
            openCamera()
        }
    }

    override fun addSender(mChatsResponse: ChatsResponse, type: Int) {
        mMessageAdapter?.addModel(mChatsResponse, type)
    }

    override fun addReceiver(mChatsResponse: ChatsResponse, type: Int) {
        mMessageAdapter?.addModel(mChatsResponse, type)
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
        recycler_view_message.setOnItemClickListener { parent, viewType, view, position ->
        }
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

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
        mPresenter.removeEventListenerSeenMessage()
    }

    override fun onBackPressed() {
        if (ll_emoji.isShown) ll_emoji.gone()
        else super.onBackPressed()
    }

    override fun onTakeImageFileCaptureSuccess(cameraFilePath: String, uriImage: Uri) {
        mPresenter.sendImageMessage(
            receiver,
            uriImage,
            receiverToken,
            AppConstant.TypeMessage.IMAGE
        )
    }
}