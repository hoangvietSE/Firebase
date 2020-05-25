package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soict.hoangviet.baseproject.extension.inflate
import com.soict.hoangviet.baseproject.extension.loadImageMessage
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.application.BaseApplication
import com.soict.hoangviet.firebase.data.network.response.ChatsResponse
import com.soict.hoangviet.firebase.extension.visible
import com.soict.hoangviet.firebase.utils.AppConstant
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_message_receiver.*
import kotlinx.android.synthetic.main.item_message_receiver_emoji.*
import kotlinx.android.synthetic.main.item_message_receiver_image_album.*
import kotlinx.android.synthetic.main.item_message_receiver_image_capture.*
import kotlinx.android.synthetic.main.item_message_sender.*
import kotlinx.android.synthetic.main.item_message_sender_emoji.*
import kotlinx.android.synthetic.main.item_message_sender_image_album.*
import kotlinx.android.synthetic.main.item_message_sender_image_capture.*
import kotlinx.android.synthetic.main.item_message_sender_image_capture.tv_seen_image_capture


class MessageAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context) {
    companion object {
        const val VIEW_TYPE_SENDER = 0
        const val VIEW_TYPE_RECEIVER = 1
        const val VIEW_TYPE_SENDER_EMOJI = 2
        const val VIEW_TYPE_RECEIVER_EMOJI = 3
        const val VIEW_TYPE_SENDER_IMAGE_CAPTURE = 4
        const val VIEW_TYPE_RECEIVER_IMAGE_CAPTURE = 5
        const val VIEW_TYPE_SENDER_ALBUM = 6
        const val VIEW_TYPE_RECEIVER_ALBUM = 7
    }

    override fun solveOnCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder? {
        return when (viewType) {
            VIEW_TYPE_SENDER -> {
                return initSenderMessageViewHolder(parent)
            }
            VIEW_TYPE_RECEIVER -> {
                return initReceiverMessageViewHolder(parent)
            }
            VIEW_TYPE_SENDER_EMOJI -> {
                return initSenderEmojiViewHolder(parent)
            }
            VIEW_TYPE_RECEIVER_EMOJI -> {
                return initReceiverEmojiViewHolder(parent)
            }
            VIEW_TYPE_SENDER_IMAGE_CAPTURE -> {
                return initSenderImageCaptureViewHolder(parent)
            }
            VIEW_TYPE_RECEIVER_IMAGE_CAPTURE -> {
                return initReceiverImageCaptureViewHolder(parent)
            }
            VIEW_TYPE_SENDER_ALBUM -> {
                return initSenderAlbum(parent)
            }
            VIEW_TYPE_RECEIVER_ALBUM -> {
                return initReceiverAlbum(parent)
            }
            else -> super.solveOnCreateViewHolder(parent, viewType)
        }
    }

    override fun solveOnBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.solveOnBindViewHolder(holder, position)
        when (getItemViewType(position)) {
            VIEW_TYPE_SENDER -> {
                bindSenderMessageViewHolder(holder, position)
            }
            VIEW_TYPE_RECEIVER -> {
                bindReceiverMessageViewHolder(holder, position)
            }
            VIEW_TYPE_SENDER_EMOJI -> {
                bindSenderEmojiViewHolder(holder, position)
            }
            VIEW_TYPE_RECEIVER_EMOJI -> {
                bindReceiverEmojiViewHolder(holder, position)
            }
            VIEW_TYPE_SENDER_IMAGE_CAPTURE -> {
                bindSenderImageCaptureViewHolder(holder, position)
            }
            VIEW_TYPE_RECEIVER_IMAGE_CAPTURE -> {
                bindReceiverImageCaptureViewHolder(holder, position)
            }
            VIEW_TYPE_SENDER_ALBUM -> {
                bindSenderAlbum(holder, position)
            }
            VIEW_TYPE_RECEIVER_ALBUM -> {
                bindReceiverAlbum(holder, position)
            }
        }
    }

    override fun initNormalViewHolder(parent: ViewGroup, viewType: Int): NormalViewHolder? {
        return null
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
    }

    //TEXT
    private fun initSenderMessageViewHolder(parent: ViewGroup) =
        SenderViewHolder(parent.inflate(R.layout.item_message_sender))

    private fun initReceiverMessageViewHolder(parent: ViewGroup) =
        ReceiverViewHolder(parent.inflate(R.layout.item_message_receiver))

    //EMOJI
    private fun initSenderEmojiViewHolder(parent: ViewGroup) =
        SenderEmojiViewHolder(parent.inflate(R.layout.item_message_sender_emoji))

    private fun initReceiverEmojiViewHolder(parent: ViewGroup) =
        ReceiverEmojiViewHolder(parent.inflate(R.layout.item_message_receiver_emoji))

    //IMAGE CAPTURE
    private fun initSenderImageCaptureViewHolder(parent: ViewGroup) =
        SenderImageCaptureViewHolder(parent.inflate(R.layout.item_message_sender_image_capture))

    private fun initReceiverImageCaptureViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? =
        ReceiverImageCaptureViewHolder(parent.inflate(R.layout.item_message_receiver_image_capture))

    //ALBUM
    private fun initSenderAlbum(parent: ViewGroup): RecyclerView.ViewHolder? {
        return SenderAlbumViewHolder(parent.inflate(R.layout.item_message_sender_image_album))
    }

    private fun initReceiverAlbum(parent: ViewGroup): RecyclerView.ViewHolder? {
        return ReceiverAlbumViewHolder(parent.inflate(R.layout.item_message_receiver_image_album))
    }

    //TEXT
    private fun bindSenderMessageViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val senderViewHolder: SenderViewHolder = holder as SenderViewHolder
        senderViewHolder.bind(getItemPosition(position, ChatsResponse::class.java))
    }

    private fun bindReceiverMessageViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val receiverViewHolder: ReceiverViewHolder = holder as ReceiverViewHolder
        receiverViewHolder.bind(getItemPosition(position, ChatsResponse::class.java))
    }

    //EMOJI
    private fun bindSenderEmojiViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val receiverViewHolder: SenderEmojiViewHolder = holder as SenderEmojiViewHolder
        receiverViewHolder.bind(getItemPosition(position, ChatsResponse::class.java))
    }

    private fun bindReceiverEmojiViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val receiverViewHolder: ReceiverEmojiViewHolder = holder as ReceiverEmojiViewHolder
        receiverViewHolder.bind(getItemPosition(position, ChatsResponse::class.java))
    }

    //IMAGE CAPTURE
    private fun bindSenderImageCaptureViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val senderViewHolder: SenderImageCaptureViewHolder = holder as SenderImageCaptureViewHolder
        senderViewHolder.bind(getItemPosition(position, ChatsResponse::class.java))
    }

    private fun bindReceiverImageCaptureViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val receiverViewHolder: ReceiverImageCaptureViewHolder =
            holder as ReceiverImageCaptureViewHolder
        receiverViewHolder.bind(getItemPosition(position, ChatsResponse::class.java))
    }

    //ALBUM
    private fun bindSenderAlbum(holder: RecyclerView.ViewHolder, position: Int) {
        val senderViewHolder: SenderAlbumViewHolder = holder as SenderAlbumViewHolder
        senderViewHolder.bind(getItemPosition(position, ChatsResponse::class.java))
    }

    private fun bindReceiverAlbum(holder: RecyclerView.ViewHolder, position: Int) {
        val receiverViewHolder: ReceiverAlbumViewHolder =
            holder as ReceiverAlbumViewHolder
        receiverViewHolder.bind(getItemPosition(position, ChatsResponse::class.java))
    }

    class SenderViewHolder(override val containerView: View?) : NormalViewHolder(containerView!!),
        LayoutContainer {
        override fun <T> bind(data: T) {
            data as ChatsResponse
            tv_message_sender.text = data.message
            tv_seen.text = when (data.seen) {
                AppConstant.UNSEND -> "Đang gửi"
                AppConstant.UNSEEN -> "Đã gửi"
                else -> "Đã xem"
            }
        }
    }

    class ReceiverViewHolder(override val containerView: View?) : NormalViewHolder(containerView!!),
        LayoutContainer {
        override fun <T> bind(data: T) {
            data as ChatsResponse
            tv_message_receiver.text = data.message
        }
    }

    class SenderEmojiViewHolder(override val containerView: View?) :
        NormalViewHolder(containerView!!),
        LayoutContainer {
        override fun <T> bind(data: T) {
            data as ChatsResponse
            Glide.with(itemView.context)
                .load(BitmapFactory.decodeStream(itemView.context.assets.open(data.message)))
                .override(200, 200)
                .centerCrop()
                .into(imv_sender_emoji)
            tv_seen_emoji.text = when (data.seen) {
                AppConstant.UNSEND -> BaseApplication.instance.getString(R.string.message_adapter_sending)
                AppConstant.UNSEEN -> BaseApplication.instance.getString(R.string.message_adapter_sent)
                else -> BaseApplication.instance.getString(R.string.message_adapter_seen)
            }
        }
    }

    class ReceiverEmojiViewHolder(override val containerView: View?) :
        NormalViewHolder(containerView!!),
        LayoutContainer {
        override fun <T> bind(data: T) {
            data as ChatsResponse
            Glide.with(itemView.context)
                .load(BitmapFactory.decodeStream(itemView.context.assets.open(data.message)))
                .override(200, 200)
                .centerCrop()
                .into(imv_receiver_emoji)
        }
    }

    class SenderImageCaptureViewHolder(override val containerView: View?) :
        NormalViewHolder(containerView!!),
        LayoutContainer {
        override fun <T> bind(data: T) {
            data as ChatsResponse
            when (data.seen) {
                AppConstant.UNSEND -> {
                    progress_loading_image.visible()
                }
                else -> {
                    imv_sender_image_capture.loadImageMessage(
                        itemView.context,
                        data.message
                    )
                }
            }
            tv_seen_image_capture.text = when (data.seen) {
                AppConstant.UNSEND -> BaseApplication.instance.getString(R.string.message_adapter_sending)
                AppConstant.UNSEEN -> BaseApplication.instance.getString(R.string.message_adapter_sent)
                else -> BaseApplication.instance.getString(R.string.message_adapter_seen)
            }
        }
    }

    class ReceiverImageCaptureViewHolder(override val containerView: View?) :
        NormalViewHolder(containerView!!),
        LayoutContainer {
        override fun <T> bind(data: T) {
            data as ChatsResponse
            imv_receiver_image_capture.setImageResource(android.R.color.transparent)
            imv_receiver_image_capture.loadImageMessage(
                itemView.context,
                data.message
            )
        }
    }

    class SenderAlbumViewHolder(override val containerView: View?) :
        NormalViewHolder(containerView!!), LayoutContainer {
        override fun <T> bind(data: T) {
            data as ChatsResponse
            val messageAlbumAdapter = MessageAlbumAdapter(itemView.context)
            rcv_sender_msg_album.adapter = messageAlbumAdapter
            val gridLayoutManager =
                object : GridLayoutManager(itemView.context, 3, GridLayoutManager.VERTICAL, false) {
                    override fun canScrollVertically(): Boolean {
                        return false
                    }

                    override fun isLayoutRTL(): Boolean {
                        return true
                    }
                }
            rcv_sender_msg_album.layoutManager = gridLayoutManager
            rcv_sender_msg_album.setOnClickListener {

            }
            messageAlbumAdapter.addModels(data.listImage!!, false)
        }
    }

    class ReceiverAlbumViewHolder(override val containerView: View?) :
        NormalViewHolder(containerView!!), LayoutContainer {
        override fun <T> bind(data: T) {
            data as ChatsResponse
            val messageAlbumAdapter = MessageAlbumAdapter(itemView.context)
            rcv_receiver_msg_album.adapter = messageAlbumAdapter
            rcv_receiver_msg_album.layoutManager = GridLayoutManager(itemView.context, 3)
            rcv_receiver_msg_album.setOnClickListener {

            }
            messageAlbumAdapter.addModels(data.listImage!!, false)
        }

    }
}