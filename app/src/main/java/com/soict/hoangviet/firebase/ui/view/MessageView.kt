package com.soict.hoangviet.firebase.ui.view

import com.soict.hoangviet.firebase.data.network.response.ChatsResponse
import com.soict.hoangviet.firebase.data.network.response.User

interface MessageView : BaseView {
    fun onSendSuccess()
    fun onShowMessage()
    fun addSender(mChatsResponse: ChatsResponse)
    fun addSenderEmoji(mChatsResponse: ChatsResponse)
    fun addReceiver(mChatsResponse: ChatsResponse)
    fun addReceiverEmoji(mChatsResponse: ChatsResponse)
    fun clearMessage()
    fun onOnlineMessage()
    fun onOfflineMessage()
    fun showInfoUserMessage(user: User)
}