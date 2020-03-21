package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soict.hoangviet.baseproject.extension.inflate
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.data.network.response.ChatsResponse
import com.soict.hoangviet.firebase.extension.inflate
import com.soict.hoangviet.firebase.extension.loadImageUrl
import com.soict.hoangviet.firebase.utils.AppConstant
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_message_receiver.*
import kotlinx.android.synthetic.main.item_message_receiver.view.*
import kotlinx.android.synthetic.main.item_message_sender.*
import kotlinx.android.synthetic.main.item_message_sender.view.*
import kotlinx.android.synthetic.main.item_message_sender.view.tv_message_sender

class MessageAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context) {
    companion object {
        const val VIEW_TYPE_SENDER = 0
        const val VIEW_TYPE_RECEIVER = 1
    }

    override fun solveOnCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder? {
        when (viewType) {
            VIEW_TYPE_SENDER -> {
                return initSenderMessageViewHolder(parent, viewType)
            }
            VIEW_TYPE_RECEIVER -> {
                return initReceiverMessageViewHolder(parent, viewType)
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
        }
    }

    override fun initNormalViewHolder(parent: ViewGroup, viewType: Int): NormalViewHolder? {
        return null
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
    }

    private fun initSenderMessageViewHolder(parent: ViewGroup, viewType: Int) = SenderViewHolder(parent.inflate(R.layout.item_message_sender))

    private fun initReceiverMessageViewHolder(parent: ViewGroup, viewType: Int) = ReceiverViewHolder(parent.inflate(R.layout.item_message_receiver))

    private fun bindSenderMessageViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val senderViewHolder: SenderViewHolder = holder as SenderViewHolder
        senderViewHolder.bind(getItemPosition(position, ChatsResponse::class.java))
    }

    private fun bindReceiverMessageViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val receiverViewHolder: ReceiverViewHolder = holder as ReceiverViewHolder
        receiverViewHolder.bind(getItemPosition(position, ChatsResponse::class.java))
    }

    class SenderViewHolder(override val containerView: View?) : NormalViewHolder(containerView!!), LayoutContainer {
        override fun <T> bind(data: T) {
            data as ChatsResponse
            tv_message_sender.text = data.message
            tv_seen.text = if (data.seen == AppConstant.UNSEEN) "Đã gửi" else "Đã xem"
        }
    }

    class ReceiverViewHolder(override val containerView: View?) : NormalViewHolder(containerView!!), LayoutContainer {
        override fun <T> bind(data: T) {
            data as ChatsResponse
            tv_message_receiver.text = data.message
        }
    }
}