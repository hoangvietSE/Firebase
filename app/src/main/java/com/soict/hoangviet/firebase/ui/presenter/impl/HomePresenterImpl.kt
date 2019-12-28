package com.soict.hoangviet.firebase.ui.presenter.impl

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.soict.hoangviet.firebase.data.network.response.ChatsResponse
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.interactor.HomeInteractor
import com.soict.hoangviet.firebase.ui.presenter.HomePresenter
import com.soict.hoangviet.firebase.ui.view.HomeView
import com.soict.hoangviet.firebase.ui.view.impl.MainActivity

class HomePresenterImpl(mView: HomeView, mInteractor: HomeInteractor) :
    BasePresenterImpl<HomeView, HomeInteractor>(mView, mInteractor), HomePresenter {
    private var mListUserChatId: ArrayList<String> = arrayListOf()
    private var mListUserChat: ArrayList<User> = arrayListOf()
    override fun getAllChatUsers() {
        val ref = datebaseRef.getReference("Chats")
        val chatsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mListUserChatId.clear()
                for (snapshot: DataSnapshot in dataSnapshot.children) {
                    val mChatsResponse: ChatsResponse =
                        snapshot.getValue(ChatsResponse::class.java)!!
                    if (mChatsResponse.sender.equals(currentId)) {
                        mListUserChatId.add(mChatsResponse.receiver)
                    }
                    if (mChatsResponse.receiver.equals(currentId)) {
                        mListUserChatId.add(mChatsResponse.sender)
                    }
                }
                fetchAll()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(MainActivity.TAG, "loadPost:onCancelled", databaseError.toException())
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        }
        ref.addValueEventListener(chatsListener)
    }

    private fun fetchAll() {
        val ref = datebaseRef.getReference("Users")
        val userChatsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mListUserChat.clear()
                for (snapshot in dataSnapshot.children) {
                    // Get Post object and use the values to update the UI
                    val user: User = snapshot.getValue(User::class.java)!!
                    // [START_EXCLUDE]
                    for (chatId: String in mListUserChatId) {
                        if (user.id.equals(chatId)) {
                            if (mListUserChat.size > 0) {
                                var check = true
                                for (userChat in mListUserChat) {
                                    if (userChat.id.equals(user.id)) {
                                        check = false
                                        break;
                                    }
                                }
                                if (check) {
                                    mListUserChat.add(user)
                                }
                            } else {
                                mListUserChat.add(user)
                            }
                        }
                    }
                    // [END_EXCLUDE]
                }
                mView?.showAllChatUsers(mListUserChat)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(MainActivity.TAG, "loadPost:onCancelled", databaseError.toException())
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        }
        ref.addValueEventListener(userChatsListener)
    }
}