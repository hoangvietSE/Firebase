package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soict.hoangviet.baseproject.extension.inflate
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.data.network.response.ChatsResponse
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.extension.visible
import com.soict.hoangviet.firebase.utils.AppConstant
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_message_receiver.*
import kotlinx.android.synthetic.main.item_message_receiver_emoji.*
import kotlinx.android.synthetic.main.item_message_receiver_image_capture.*
import kotlinx.android.synthetic.main.item_message_sender.*
import kotlinx.android.synthetic.main.item_message_sender_emoji.*
import kotlinx.android.synthetic.main.item_message_sender_image_capture.*


class MessageAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context) {
    companion object {
        const val VIEW_TYPE_SENDER = 0
        const val VIEW_TYPE_RECEIVER = 1
        const val VIEW_TYPE_SENDER_EMOJI = 2
        const val VIEW_TYPE_RECEIVER_EMOJI = 3
        const val VIEW_TYPE_SENDER_IMAGE_CAPTURE = 4
        const val VIEW_TYPE_RECEIVER_IMAGE_CAPTURE = 5
    }

    override fun solveOnCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder? {
        when (viewType) {
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
            VIEW_TYPE_SENDER_IMAGE_CAPTURE -> {
                return initReceiverImageCaptureViewHolder(parent)
            }
        }
        return super.solveOnCreateViewHolder(parent, viewType)
    }

    override fun solveOnBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.solveOnBindViewHolder(holder, position)
        when (mListWrapperModel[position].viewType) {
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
        }
    }

    override fun initNormalViewHolder(parent: ViewGroup, viewType: Int): NormalViewHolder? {
        return null
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
    }

    private fun initSenderMessageViewHolder(parent: ViewGroup) =
        SenderViewHolder(parent.inflate(R.layout.item_message_sender))

    private fun initReceiverMessageViewHolder(parent: ViewGroup) =
        ReceiverViewHolder(parent.inflate(R.layout.item_message_receiver))

    private fun initSenderEmojiViewHolder(parent: ViewGroup) =
        SenderEmojiViewHolder(parent.inflate(R.layout.item_message_sender_emoji))

    private fun initReceiverEmojiViewHolder(parent: ViewGroup) =
        ReceiverEmojiViewHolder(parent.inflate(R.layout.item_message_receiver_emoji))

    private fun initSenderImageCaptureViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? =
        SenderImageCaptureViewHolder(parent.inflate(R.layout.item_message_sender_image_capture))

    private fun initReceiverImageCaptureViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? =
        ReceiverImageCaptureViewHolder(parent.inflate(R.layout.item_message_receiver_image_capture))


    private fun bindSenderMessageViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val senderViewHolder: SenderViewHolder = holder as SenderViewHolder
        senderViewHolder.bind(getItemPosition(position, ChatsResponse::class.java))
    }

    private fun bindReceiverMessageViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val receiverViewHolder: ReceiverViewHolder = holder as ReceiverViewHolder
        receiverViewHolder.bind(getItemPosition(position, ChatsResponse::class.java))
    }

    private fun bindSenderEmojiViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val receiverViewHolder: SenderEmojiViewHolder = holder as SenderEmojiViewHolder
        receiverViewHolder.bind(getItemPosition(position, ChatsResponse::class.java))
    }

    private fun bindReceiverEmojiViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val receiverViewHolder: ReceiverEmojiViewHolder = holder as ReceiverEmojiViewHolder
        receiverViewHolder.bind(getItemPosition(position, ChatsResponse::class.java))
    }

    private fun bindSenderImageCaptureViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    private fun bindReceiverImageCaptureViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

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
            itemView.setOnClickListener {
                if (tv_seen.isShown) {
                    tv_seen.gone()
                } else {
                    tv_seen.visible()
                }
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
                .into(imv_sender_emoji)
            tv_seen_emoji.text = when (data.seen) {
                AppConstant.UNSEND -> "Đang gửi"
                AppConstant.UNSEEN -> "Đã gửi"
                else -> "Đã xem"
            }
            itemView.setOnClickListener {
                if (tv_seen_emoji.isShown) {
                    tv_seen_emoji.gone()
                } else {
                    tv_seen_emoji.visible()
                }
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
                .into(imv_receiver_emoji)
        }
    }

    class SenderImageCaptureViewHolder(override val containerView: View?) :
        NormalViewHolder(containerView!!),
        LayoutContainer {
        override fun <T> bind(data: T) {
            data as ChatsResponse
            Glide.with(itemView.context)
                .load(Uri.parse(data.message))
                .into(imv_sender_image_capture)
            tv_seen_image_capture.text = when (data.seen) {
                AppConstant.UNSEND -> "Đang gửi"
                AppConstant.UNSEEN -> "Đã gửi"
                else -> "Đã xem"
            }
            itemView.setOnClickListener {
                if (tv_seen_image_capture.isShown) {
                    tv_seen_image_capture.gone()
                } else {
                    tv_seen_image_capture.visible()
                }
            }
        }
    }

    class ReceiverImageCaptureViewHolder(override val containerView: View?) :
        NormalViewHolder(containerView!!),
        LayoutContainer {
        override fun <T> bind(data: T) {
            data as ChatsResponse
            Glide.with(itemView.context)
                .load(Uri.parse(data.message))
                .into(imv_receiver_image_capture)
        }
    }
}