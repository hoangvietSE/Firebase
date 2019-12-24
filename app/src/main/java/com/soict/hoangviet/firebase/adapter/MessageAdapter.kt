package com.soict.hoangviet.firebase.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.data.network.response.ChatsResponse
import com.soict.hoangviet.firebase.extension.inflate
import com.soict.hoangviet.firebase.extension.loadImageUrl
import kotlinx.android.synthetic.main.item_message_receiver.view.*
import kotlinx.android.synthetic.main.item_message_sender.view.*
import kotlinx.android.synthetic.main.item_message_sender.view.tv_message_sender

class MessageAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context) {
    companion object {
        const val VIEW_TYPE_SENDER = 0
        const val VIEW_TYPE_RECEIVER = 1
    }

    override fun initLoadingViewHolder(parent: ViewGroup, viewType: Int): LoadingViewHolder {
        return LoadingViewHolder(context.inflate(R.layout.layout_loadmore))
    }

    override fun onBindLoadingViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun solveOnCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
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

    override fun initNormalViewHolder(parent: ViewGroup, viewType: Int): NormalViewHoler? {
        return null
    }

    override fun bindNormalViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    private fun initSenderMessageViewHolder(parent: ViewGroup, viewType: Int): NormalViewHoler? {
        return SenderViewHolder(context.inflate(R.layout.item_message_sender, parent, false))
    }

    private fun initReceiverMessageViewHolder(parent: ViewGroup, viewType: Int): NormalViewHoler? {
        return ReceiverViewHolder(context.inflate(R.layout.item_message_receiver, parent, false))
    }

    private fun bindSenderMessageViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val senderViewHolder: SenderViewHolder = holder as SenderViewHolder
        val data: ChatsResponse = getItemPosition(position, ChatsResponse::class.java)
        senderViewHolder.tvMessageSender.text = data.message
    }

    private fun bindReceiverMessageViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val receiverViewHolder: ReceiverViewHolder = holder as ReceiverViewHolder
        val data: ChatsResponse = getItemPosition(position, ChatsResponse::class.java)
        receiverViewHolder.tvMessageReceiver.text = data.message
    }

    class SenderViewHolder(itemView: View) : NormalViewHoler(itemView) {
        val tvMessageSender: TextView = itemView.tv_message_sender
    }

    class ReceiverViewHolder(itemView: View) : NormalViewHoler(itemView) {
        val imvAvatarReceiver: ImageView = itemView.imv_avatar_receiver
        val tvMessageReceiver: TextView = itemView.tv_message_receiver
    }
}