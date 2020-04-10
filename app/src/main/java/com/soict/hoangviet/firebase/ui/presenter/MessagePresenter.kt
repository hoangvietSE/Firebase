package com.soict.hoangviet.firebase.ui.presenter

import androidx.appcompat.view.menu.MenuView
import com.soict.hoangviet.firebase.ui.interactor.MessageInteractor
import com.soict.hoangviet.firebase.ui.view.MessageView

interface MessagePresenter : BasePresenter<MessageView, MessageInteractor> {
    fun sendMessage(receiver: String, msg: String, receiverToken: String, type: Int = 0)
    fun readMessage(receiver: String)
    fun showInfoUserMessage(receiver: String?)
    fun seenMessage(receiver: String)
    fun removeEventListenerSeenMessage()
}