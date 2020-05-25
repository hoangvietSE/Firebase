package com.soict.hoangviet.firebase.ui.presenter

import android.net.Uri
import androidx.appcompat.view.menu.MenuView
import com.soict.hoangviet.firebase.ui.interactor.MessageInteractor
import com.soict.hoangviet.firebase.ui.view.MessageView

interface MessagePresenter : BasePresenter<MessageView, MessageInteractor> {
    fun sendMessage(receiver: String, msg: String = "", receiverToken: String, type: Int = 0, listImage: ArrayList<String> = arrayListOf())
    fun readMessage(receiver: String)
    fun showInfoUserMessage(receiver: String?)
    fun seenMessage(receiver: String)
    fun removeEventListenerSeenMessage()
    fun sendImageMessage(receiver: String, uriImage: Uri, receiverToken: String, image: Int)
    fun sendAlbumMessage(
        receiver: String,
        path: ArrayList<Uri>?,
        receiverToken: String,
        image: Int
    )

    fun checkEnablePushNotification(receiver: String)
}