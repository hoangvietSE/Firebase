package com.soict.hoangviet.firebase.ui.view.impl

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.speech.RecognizerIntent
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import com.sangcomz.fishbun.define.Define
import com.soict.hoangviet.baseproject.extension.hideFragmentByTag
import com.soict.hoangviet.baseproject.extension.hideSoftKeyboard
import com.soict.hoangviet.baseproject.extension.launchActivity
import com.soict.hoangviet.baseproject.extension.onAvoidDoubleClick
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.adapter.EmojiParentAdapter
import com.soict.hoangviet.firebase.adapter.MessageAdapter
import com.soict.hoangviet.firebase.common.BasePhotoActivity
import com.soict.hoangviet.firebase.custom.dialogfragment.ZoomImageDialogFragment
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
import kotlinx.android.synthetic.main.item_message_sender_emoji.view.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class MessageActivity : BasePhotoActivity(), MessageView {
    companion object {
        const val EXTRA_USER_ID = "extra_user_id"
        const val TOKEN_DEVICE_ID = "token_device_id"
        const val WAVE_HAND = "wave_hand"
        const val FACE_EMOJI = 0
        const val FISH_EMOJI = 1
        const val KITTY_EMOJI = 2
        const val REQUEST_CODE_SPEECH = 1009
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
    private val waveHand: Boolean by lazy {
        intent.getBooleanExtra(WAVE_HAND, false)
    }
    var path = ArrayList<Uri>()
    lateinit var zoomImageDialogFragment: ZoomImageDialogFragment

    override fun initView() {
        mPresenter.onAttach(this)
        initAdapter()
        getDataIntent()
        readMessage()
        seenMessage()
        initEmojiAdapter()
        sendWaveHand()
    }

    private fun sendWaveHand() {
        if (waveHand) {
            mPresenter.sendMessage(
                receiver,
                "emoji/hi_emoji/wavehand.png",
                receiverToken,
                AppConstant.TypeMessage.EMOJI
            )
        }
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
        mPresenter.checkEnablePushNotification(receiver)
    }

    override fun initListener() {
        btn_send.onAvoidDoubleClick {
            val msg: String = edt_message.text.toString()
            if (!TextUtils.isEmpty(msg)) {
                mPresenter.sendMessage(receiver, msg, receiverToken)
            }
        }
        toolbar.imvLeft?.onAvoidDoubleClick {
            finish()
        }
        edt_message.setOnTouchListener { v, event ->
            if (ll_emoji.isShown) ll_emoji.gone()
            false
        }
        btn_emoj.onAvoidDoubleClick {
            hideSoftKeyboard()
            Handler().postDelayed({
                when {
                    ll_emoji.isShown -> ll_emoji.gone()
                    else -> ll_emoji.visible()
                }
            }, 300)
        }
        btn_send_image_capture.onAvoidDoubleClick {
            openCamera()
        }
        btn_send_image_album.onAvoidDoubleClick {
            openAlbumFishbun()
        }
        toolbar.imvRightOne?.setOnClickListener {
            launchActivity<InfoActivity>()
        }
        btn_send_speech.setOnClickListener {
            openSpeechApi()
        }
    }

    private fun openSpeechApi() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak")
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(
                applicationContext,
                getString(R.string.device_not_support),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun openAlbumFishbun() {
        FishBun.with(this)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(5)
            .setMinCount(1)
            .setActionBarColor(Color.parseColor("#7505b7"), Color.parseColor("#5f00a8"), true)
            .startAlbum()
    }

    override fun addSender(mChatsResponse: ChatsResponse, type: Int) {
        mMessageAdapter?.addModel(mChatsResponse, type, isSelected = false, isScroolToLast = true)
    }

    override fun addReceiver(mChatsResponse: ChatsResponse, type: Int) {
        mMessageAdapter?.addModel(mChatsResponse, type, isSelected = false, isScroolToLast = true)
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
            when (viewType) {
                MessageAdapter.VIEW_TYPE_SENDER -> showHideSeenMessage(view.tv_seen)
                MessageAdapter.VIEW_TYPE_SENDER_EMOJI -> showHideSeenMessage(view.tv_seen_emoji)
                MessageAdapter.VIEW_TYPE_SENDER_IMAGE_CAPTURE -> {
                    zoomImageDialogFragment(
                        mMessageAdapter?.getItemPosition(
                            position!!,
                            ChatsResponse::class.java
                        )!!
                    )
                }
            }
        }
        recycler_view_message.setLinearLayoutManagerMessage()
        recycler_view_message.disableRefreshing()
    }

    private fun zoomImageDialogFragment(data: ChatsResponse) {
        zoomImageDialogFragment = ZoomImageDialogFragment.getInstance(data.message)
        hideFragmentByTag("zoomImageDialogFragment")
        zoomImageDialogFragment.show(supportFragmentManager, "zoomImageDialogFragment")
    }

    private fun showHideSeenMessage(view: View) {
        if (view.isShown) {
            view.gone()
        } else {
            view.visible()
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Define.ALBUM_REQUEST_CODE && resultCode == RESULT_OK) {
            path = data!!.getParcelableArrayListExtra(Define.INTENT_PATH)
            when (path.size) {
                1 -> {
                    mPresenter.sendImageMessage(
                        receiver,
                        path[0],
                        receiverToken,
                        AppConstant.TypeMessage.IMAGE
                    )
                }
                else -> {
                    mPresenter.sendAlbumMessage(
                        receiver,
                        path,
                        receiverToken,
                        AppConstant.TypeMessage.ALBUM
                    )
                }
            }
        }
        when (requestCode) {
            REQUEST_CODE_SPEECH -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.let {
                        mPresenter.sendMessage(
                            receiver,
                            it.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0).toString(),
                            receiverToken
                        )
                    }
                }
            }
        }
    }
}