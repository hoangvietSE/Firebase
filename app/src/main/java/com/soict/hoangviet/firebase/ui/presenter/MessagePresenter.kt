package com.soict.hoangviet.firebase.ui.presenter

interface MessagePresenter : BasePresenter {
    fun sendMessage(receiver: String, msg: String)
    fun readMessage(receiver: String)
    fun showInfoUserMessage(receiver: String?)
    fun seenMessage(receiver: String)
    fun removeEventListenerSeenMessage()
}