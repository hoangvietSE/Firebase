package com.soict.hoangviet.firebase.ui.presenter.impl

import android.util.Log
import com.google.firebase.database.*
import com.soict.hoangviet.firebase.application.BaseApplication
import com.soict.hoangviet.firebase.data.network.response.ChatsResponse
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.data.sharepreference.AppSharePreference
import com.soict.hoangviet.firebase.ui.interactor.MessageInteractor
import com.soict.hoangviet.firebase.ui.presenter.MessagePresenter
import com.soict.hoangviet.firebase.ui.view.MessageView
import com.soict.hoangviet.firebase.ui.view.impl.MainActivity
import com.soict.hoangviet.firebase.utils.AppConstant

class MessagePresenterImpl(mView: MessageView, mInteractor: MessageInteractor) :
    BasePresenterImpl<MessageView, MessageInteractor>(mView, mInteractor), MessagePresenter {
    private val messageRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private var mListChats: ArrayList<ChatsResponse> = arrayListOf()
    private var seenMessageRef: DatabaseReference? = null
    private var seenMessageListener: ValueEventListener? = null
    override fun sendMessage(receiver: String, msg: String) {
        val record: MutableMap<String, Any?> = mutableMapOf()
        record["sender"] = AppSharePreference.getInstance(BaseApplication.instance).getUser()?.id
        record["receiver"] = receiver
        record["message"] = msg
        record["seen"] = AppConstant.UNSEEN
        messageRef.child("Chats").push().setValue(record)
        val ref = datebaseRef.getReference("ChatsList").child(currentId!!).child(receiver)
        val eventListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    ref.child("receiverId").setValue(receiver)
                }
            }

        }
        ref.addListenerForSingleValueEvent(eventListener)
        mView?.onSendSuccess()
    }

    override fun readMessage(receiver: String) {
        val listMessageReference: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("Chats")
        val listMessageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mView?.clearMessage()
                for (snapshot: DataSnapshot in dataSnapshot.children) {
                    // Get Post object and use the values to update the UI
                    val mChatsResponse: ChatsResponse =
                        snapshot.getValue(ChatsResponse::class.java)!!
                    // [START_EXCLUDE]
                    if ((mChatsResponse.sender == AppSharePreference.getInstance(BaseApplication.instance).getUser()!!.id
                                && mChatsResponse.receiver == receiver)
                    ) {
                        mView?.addSender(mChatsResponse)
                    }
                    if ((mChatsResponse.receiver == AppSharePreference.getInstance(BaseApplication.instance).getUser()!!.id
                                && mChatsResponse.sender == receiver)
                    ) {
                        mView?.addReceiver(mChatsResponse)
                    }
                    // [END_EXCLUDE]
                }
                mView?.onShowMessage()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(MainActivity.TAG, "loadPost:onCancelled", databaseError.toException())
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        }
        listMessageReference.addValueEventListener(listMessageListener as ValueEventListener)
    }

    override fun showInfoUserMessage(receiver: String?) {
        val ref = datebaseRef.getReference("Users").child(receiver!!)
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d(MainActivity.TAG, "load:success")
                // Get Post object and use the values to update the UI
                val user = dataSnapshot.getValue(User::class.java)
                user?.let { mView?.showInfoUserMessage(it) }
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(MainActivity.TAG, "load:onCancelled", databaseError.toException())
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        }
        ref.addValueEventListener(userListener)
    }

    override fun seenMessage(receiver: String) {
        seenMessageRef = FirebaseDatabase.getInstance().getReference("Chats")
        seenMessageListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot: DataSnapshot in dataSnapshot.children) {
                    // Get Post object and use the values to update the UI
                    val mChatsResponse: ChatsResponse =
                        snapshot.getValue(ChatsResponse::class.java)!!
                    // [START_EXCLUDE]
                    if ((mChatsResponse.receiver == AppSharePreference.getInstance(BaseApplication.instance).getUser()!!.id
                                && mChatsResponse.sender == receiver)
                    ) {
                        val record: MutableMap<String, Any> = mutableMapOf()
                        record["seen"] = AppConstant.SEEN
                        snapshot.ref.updateChildren(record)
                    }
                    // [END_EXCLUDE]
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(MainActivity.TAG, "loadPost:onCancelled", databaseError.toException())
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        }
        seenMessageRef?.addValueEventListener(seenMessageListener as ValueEventListener)
    }

    override fun removeEventListenerSeenMessage() {
        seenMessageRef?.removeEventListener(seenMessageListener as ValueEventListener)
    }
}