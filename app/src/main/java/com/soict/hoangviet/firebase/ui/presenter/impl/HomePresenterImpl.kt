package com.soict.hoangviet.firebase.ui.presenter.impl

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.soict.hoangviet.firebase.builder.DatabaseFirebase
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
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
        DatabaseFirebase.Builder()
            .reference("ChatsList")
            .child(currentId!!)
            .onDataChange {
                mListUserChatId.clear()
                for (snapshot: DataSnapshot in it.children) {
                    val mChatsListResponse: ChatsListResponse =
                        snapshot.getValue(ChatsListResponse::class.java)!!
                    mListUserChatId.add(mChatsListResponse)
                }
                fetchAll()
            }
            .onCancelled {
                // Getting Post failed, log a message
                Log.w(MainActivity.TAG, "loadPost:onCancelled", it.toException())
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
            .build()
    }

    private fun fetchAll() {
        DatabaseFirebase.Builder()
            .reference("Users")
            .onDataChange {
                mListUserChat.clear()
                for (snapshot in it.children) {
                    // Get Post object and use the values to update the UI
                    val user: User = snapshot.getValue(User::class.java)!!
                    // [START_EXCLUDE]
                    for (mChatsList in mListUserChatId) {
                        if (user.id == mChatsList.id) {
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
            .onCancelled {
                // Getting Post failed, log a message
                Log.w(MainActivity.TAG, "loadPost:onCancelled", it.toException())
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
            .build()
    }

    private fun fetchLastMessage(
        homeResponse: HomeResponse,
        position: Int,
        listener: LastMessageListener
    ) {
        DatabaseFirebase.Builder()
            .reference("Chats")
            .onDataChange {
                for (snapshot in it.children) {
                    // Get Post object and use the values to update the UI
                    val mChatResponse: ChatsResponse = snapshot.getValue(ChatsResponse::class.java)!!
                    // [START_EXCLUDE]
                    if ((mChatResponse.sender == currentId && mChatResponse.receiver == homeResponse.user?.id)
                        || (mChatResponse.sender == homeResponse.user?.id && mChatResponse.receiver == currentId)
                    ) {
                        homeResponse.lastMessage = mChatResponse.message
                        listener.onLastMessage(position)
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

    interface LastMessageListener {
        fun onLastMessage(position: Int)
    }
}