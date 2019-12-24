package com.soict.hoangviet.firebase.ui.view

import com.soict.hoangviet.firebase.data.network.response.ChatsResponse

interface MessageView : BaseView {
    fun onSendSuccess()
    fun onShowMessage()
    fun addSender(mChatsResponse: ChatsResponse)
    fun addReceiver(mChatsResponse: ChatsResponse)
    fun clearMessage()
}