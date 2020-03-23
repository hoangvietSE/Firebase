package com.soict.hoangviet.firebase.ui.presenter.impl

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.soict.hoangviet.baseproject.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.data.network.response.ChatsListResponse
import com.soict.hoangviet.firebase.data.network.response.ChatsResponse
import com.soict.hoangviet.firebase.data.network.response.HomeResponse
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.interactor.HomeInteractor
import com.soict.hoangviet.firebase.ui.presenter.HomePresenter
import com.soict.hoangviet.firebase.ui.view.HomeView
import com.soict.hoangviet.firebase.ui.view.impl.MainActivity
import javax.inject.Inject

class HomePresenterImpl @Inject internal constructor(
    homeInteractor: HomeInteractor,
    sharePreference: SharePreference
) : BasePresenterImpl<HomeView, HomeInteractor>(
    mInteractor = homeInteractor,
    mAppSharePreference = sharePreference
), HomePresenter {
    private var mListUserChatId: ArrayList<ChatsListResponse> = arrayListOf()
    private var mListUserChat: ArrayList<HomeResponse> = arrayListOf()
    override fun getAllChatUsers() {
        val ref = datebaseRef.getReference("ChatsList").child(currentId!!)
        val chatsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mListUserChatId.clear()
                for (snapshot: DataSnapshot in dataSnapshot.children) {
                    val mChatsListResponse: ChatsListResponse =
                        snapshot.getValue(ChatsListResponse::class.java)!!
                    mListUserChatId.add(mChatsListResponse)
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
                    for (mChatsList in mListUserChatId) {
                        if (user.id.equals(mChatsList.id)) {
                            val homeResponse = HomeResponse(user)
                            mListUserChat.add(homeResponse)
                            fetchLastMessage(
                                homeResponse,
                                mListUserChat.size - 1,
                                object : LastMessageListener {
                                    override fun onLastMessage(position: Int) {
                                        mView?.notifyChange(position)
                                    }

                                })
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

    private fun fetchLastMessage(
        homeResponse: HomeResponse,
        position: Int,
        listener: LastMessageListener
    ) {
        val ref = datebaseRef.getReference("Chats")
        val lastMessageEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    // Get Post object and use the values to update the UI
                    val mChatResponse: ChatsResponse =
                        snapshot.getValue(ChatsResponse::class.java)!!
                    // [START_EXCLUDE]
                    if ((mChatResponse.sender.equals(currentId) && mChatResponse.receiver.equals(
                            homeResponse.user?.id
                        ))
                        || (mChatResponse.sender.equals(homeResponse.user?.id) && mChatResponse.receiver.equals(
                            currentId
                        ))
                    ) {
                        homeResponse.lastMessage = mChatResponse.message
                        listener.onLastMessage(position)
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
        ref.addValueEventListener(lastMessageEventListener)
    }

    interface LastMessageListener {
        fun onLastMessage(position: Int)
    }
}