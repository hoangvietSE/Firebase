package com.soict.hoangviet.firebase.ui.presenter.impl

import android.util.Log
import com.google.firebase.database.*
import com.soict.hoangviet.firebase.builder.DatabaseFirebase
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.data.network.response.ChatsResponse
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.interactor.MessageInteractor
import com.soict.hoangviet.firebase.ui.presenter.MessagePresenter
import com.soict.hoangviet.firebase.ui.view.MessageView
import com.soict.hoangviet.firebase.ui.view.impl.MainActivity
import com.soict.hoangviet.firebase.utils.AppConstant
import javax.inject.Inject

class MessagePresenterImpl @Inject internal constructor(
    messageInteractor: MessageInteractor,
    sharePreference: SharePreference
) : BasePresenterImpl<MessageView, MessageInteractor>(
    mInteractor = messageInteractor,
    mAppSharePreference = sharePreference
), MessagePresenter {
    private val messageRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var pairMessageSender: Pair<DatabaseReference, ValueEventListener>
    private lateinit var pairMessageReceiver: Pair<DatabaseReference, ValueEventListener>
    private lateinit var pairShowAllMessage: Pair<DatabaseReference, ValueEventListener>
    private lateinit var pairShowInfoUser: Pair<DatabaseReference, ValueEventListener>
    private lateinit var pairSeenMessage: Pair<DatabaseReference, ValueEventListener>
    override fun sendMessage(receiver: String, msg: String, receiverToken: String) {
        val record: MutableMap<String, Any?> = mutableMapOf()
        record["sender"] = currentId
        record["receiver"] = receiver
        record["message"] = msg
        record["seen"] = AppConstant.UNSEEN
        messageRef.child("Chats").push().setValue(record)
        pairMessageSender = DatabaseFirebase.Builder()
            .reference("ChatsList")
            .child(currentId!!)
            .child(receiver)
            .onDataChange {
                if (!it.exists()) {
                    pairMessageSender.first.child("id").setValue(receiver)
                }
                pushNotificationToReceiver(receiver, receiverToken)
            }
            .onCancelled {
            }
            .build()
        pairMessageReceiver = DatabaseFirebase.Builder()
            .reference("ChatsList")
            .child(receiver)
            .child(currentId!!)
            .onDataChange {
                if (!it.exists()) {
                    pairMessageReceiver.first.child("id").setValue(currentId!!)
                }
            }
            .onCancelled {
            }
            .build()
        mView?.onSendSuccess()
    }

    private fun pushNotificationToReceiver(receiver: String, receiverToken: String) {
        mInteractor?.pushNotificationToReceiver(receiver, receiverToken)
            ?.subscribe({
            }, {
                handleThrowable(throwable = it)
            })
    }

    override fun readMessage(receiver: String) {
        pairShowAllMessage = DatabaseFirebase.Builder()
            .reference("Chats")
            .onDataChange {
                mView?.clearMessage()
                for (snapshot: DataSnapshot in it.children) {
                    // Get Post object and use the values to update the UI
                    val mChatsResponse: ChatsResponse =
                        snapshot.getValue(ChatsResponse::class.java)!!
                    // [START_EXCLUDE]
                    if ((mChatsResponse.sender == currentId
                                && mChatsResponse.receiver == receiver)
                    ) {
                        mView?.addSender(mChatsResponse)
                    }
                    if ((mChatsResponse.receiver == currentId
                                && mChatsResponse.sender == receiver)
                    ) {
                        mView?.addReceiver(mChatsResponse)
                    }
                    // [END_EXCLUDE]
                }
                mView?.onShowMessage()
            }
            .onCancelled {
                // Getting Post failed, log a message
                Log.w(MainActivity.TAG, "loadPost:onCancelled", it.toException())
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
            .build()
    }

    override fun showInfoUserMessage(receiver: String?) {
        pairShowInfoUser = DatabaseFirebase.Builder()
            .reference("Users")
            .child(receiver!!)
            .onDataChange {
                Log.d(MainActivity.TAG, "load:success")
                // Get Post object and use the values to update the UI
                val user = it.getValue(User::class.java)
                user?.let { mView?.showInfoUserMessage(it) }
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
            .onCancelled {
                // Getting Post failed, log a message
                Log.w(MainActivity.TAG, "load:onCancelled", it.toException())
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
            .build()
    }

    override fun seenMessage(receiver: String) {
        pairSeenMessage = DatabaseFirebase.Builder()
            .reference("Chats")
            .onDataChange {
                for (snapshot in it.children) {
                    // Get Post object and use the values to update the UI
                    val mChatsResponse: ChatsResponse =
                        snapshot.getValue(ChatsResponse::class.java)!!
                    // [START_EXCLUDE]
                    if ((mChatsResponse.receiver == currentId
                                && mChatsResponse.sender == receiver)
                    ) {
                        val record: MutableMap<String, Any> = mutableMapOf()
                        record["seen"] = AppConstant.SEEN
                        snapshot.ref.updateChildren(record)
                    }
                    // [END_EXCLUDE]
                }
            }
            .onCancelled {
                // Getting Post failed, log a message
                Log.w(MainActivity.TAG, "loadPost:onCancelled", it.toException())
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
            .build()
    }

    override fun removeEventListenerSeenMessage() {
        pairSeenMessage.first.removeEventListener(pairSeenMessage.second)
    }
}