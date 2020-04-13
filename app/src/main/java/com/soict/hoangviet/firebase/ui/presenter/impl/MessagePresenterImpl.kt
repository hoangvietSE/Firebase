package com.soict.hoangviet.firebase.ui.presenter.impl

import android.net.Uri
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.soict.hoangviet.firebase.adapter.MessageAdapter
import com.soict.hoangviet.firebase.builder.DatabaseFirebase
import com.soict.hoangviet.firebase.builder.StorageFirebase
import com.soict.hoangviet.firebase.data.network.request.DataNotification
import com.soict.hoangviet.firebase.data.network.request.MessageRequestBody
import com.soict.hoangviet.firebase.data.network.response.ChatsResponse
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.ui.interactor.MessageInteractor
import com.soict.hoangviet.firebase.ui.presenter.MessagePresenter
import com.soict.hoangviet.firebase.ui.view.MessageView
import com.soict.hoangviet.firebase.ui.view.impl.MainActivity
import com.soict.hoangviet.firebase.utils.AppConstant
import com.soict.hoangviet.firebase.utils.FileUtil
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
    override fun sendMessage(receiver: String, msg: String, receiverToken: String, type: Int) {
        val record: MutableMap<String, Any?> = mutableMapOf()
        record["sender"] = currentId
        record["type"] = type
        record["receiver"] = receiver
        record["message"] = msg
        record["seen"] = AppConstant.UNSEND
        //mChatId: Get random id for chat
        val mChatId = messageRef.child("Chats").push().key
        messageRef.child("Chats").child(mChatId!!).setValue(record)
        pairMessageSender = DatabaseFirebase.Builder()
            .reference("ChatsList")
            .child(currentId!!)
            .child(receiver)
            .onDataChange {
                if (!it.exists()) {
                    pairMessageSender.first.child("id").setValue(receiver)
                }
                when (type) {
                    AppConstant.TypeMessage.IMAGE -> detectNetworkPushImage(mChatId, receiver, receiverToken, msg, type)
                    else -> detectNetwork(mChatId, receiver, receiverToken)
                }
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

    private fun detectNetworkPushImage(mChatId: String, receiver: String, receiverToken: String, msg: String, type: Int) {
        var processDone = false
        DatabaseFirebase.Builder()
            .reference(".info/connected")
            .onDataChange {
                val connected = it.getValue(Boolean::class.java) ?: false
                if (connected && !processDone) {
                    Log.d("myLog", "connected")
                    StorageFirebase.Builder()
                        .child("${System.currentTimeMillis()}.${FileUtil.getFileExtension(Uri.parse(msg)!!)}")
                        .putFile(Uri.parse(msg))
                        .onCompleteListener {
                            //Change to url image
                            messageRef.child("Chats").child(mChatId).updateChildren(mapOf("message" to it.result.toString()))
                            detectNetwork(mChatId, receiver, receiverToken)
                            processDone = true
                        }
                        .onFailureListener {
                        }
                        .build()
                } else {
                    Log.d("myLog", "disconnected")
                }
            }
            .onCancelled {
            }
            .build()
    }

    //Detecting Connection State
    private fun detectNetwork(
        mChatId: String,
        receiver: String,
        receiverToken: String
    ) {
        var processDone = false
        DatabaseFirebase.Builder()
            .reference(".info/connected")
            .onDataChange {
                val connected = it.getValue(Boolean::class.java) ?: false
                if (connected && !processDone) {
                    Log.d("myLog", "connected")
                    messageRef.child("Chats").child(mChatId)
                        .updateChildren(mapOf("seen" to AppConstant.UNSEEN))
                    pushNotificationToReceiver(receiver, receiverToken)
                    processDone = true
                } else {
                    Log.d("myLog", "disconnected")
                }
            }
            .onCancelled {
            }
            .build()
    }

    private fun pushNotificationToReceiver(receiver: String, receiverToken: String) {
        mInteractor?.pushNotificationToReceiver(
            MessageRequestBody(
                DataNotification(
                    title = mAppSharePreference?.get(
                        AppConstant.SharePreference.USER,
                        User::class.java
                    )?.fullname!!,
                    body = "Bạn nhận được tin nhắn mới",
                    receiverId = mAppSharePreference?.get(
                        AppConstant.SharePreference.USER,
                        User::class.java
                    )?.id,
                    receiverToken = mAppSharePreference?.get(
                        AppConstant.SharePreference.USER,
                        User::class.java
                    )?.deviceToken
                ), receiverToken
            )
        )
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
                        when (mChatsResponse.type) {
                            AppConstant.TypeMessage.TEXT -> mView?.addSender(
                                mChatsResponse,
                                MessageAdapter.VIEW_TYPE_SENDER
                            )
                            AppConstant.TypeMessage.EMOJI -> mView?.addSender(
                                mChatsResponse,
                                MessageAdapter.VIEW_TYPE_SENDER_EMOJI
                            )
                            AppConstant.TypeMessage.IMAGE -> mView?.addSender(
                                mChatsResponse,
                                MessageAdapter.VIEW_TYPE_SENDER_IMAGE_CAPTURE
                            )
                        }
                    }
                    if ((mChatsResponse.receiver == currentId
                                && mChatsResponse.sender == receiver)
                    ) {
                        when (mChatsResponse.type) {
                            AppConstant.TypeMessage.TEXT -> mView?.addReceiver(
                                mChatsResponse,
                                MessageAdapter.VIEW_TYPE_RECEIVER
                            )
                            AppConstant.TypeMessage.EMOJI -> mView?.addReceiver(
                                mChatsResponse,
                                MessageAdapter.VIEW_TYPE_RECEIVER_EMOJI
                            )
                            AppConstant.TypeMessage.IMAGE -> {
                                if(mChatsResponse.seen != AppConstant.UNSEND) {
                                    mView?.addReceiver(
                                        mChatsResponse,
                                        MessageAdapter.VIEW_TYPE_RECEIVER_IMAGE_CAPTURE
                                    )
                                }
                            }
                        }
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

    override fun sendImageMessage(
        receiver: String,
        uriImage: Uri,
        receiverToken: String,
        type: Int
    ) {
        sendMessage(receiver, uriImage.toString(), receiverToken, type)
    }
}