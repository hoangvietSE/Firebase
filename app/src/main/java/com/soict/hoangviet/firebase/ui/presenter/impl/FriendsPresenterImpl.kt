package com.soict.hoangviet.firebase.ui.presenter.impl

import android.util.Log
import com.soict.hoangviet.firebase.builder.DatabaseFirebase
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.ui.interactor.FriendsInteractor
import com.soict.hoangviet.firebase.ui.presenter.FriendsPresenter
import com.soict.hoangviet.firebase.ui.view.FriendsView
import com.soict.hoangviet.firebase.ui.view.impl.MainActivity
import javax.inject.Inject

class FriendsPresenterImpl @Inject internal constructor(
    friendsInteractor: FriendsInteractor,
    sharePreference: SharePreference
) :
    BasePresenterImpl<FriendsView, FriendsInteractor>(
        mInteractor = friendsInteractor,
        mAppSharePreference = sharePreference
    ), FriendsPresenter {
    private var mListUser: ArrayList<User> = arrayListOf()
    override fun getAllUser() {
        DatabaseFirebase.Builder()
            .reference("Users")
            .onDataChange {
                mListUser.clear()
                for (snapshot in it.children) {
                    // Get Post object and use the values to update the UI
                    val user = snapshot.getValue(User::class.java)
                    // [START_EXCLUDE]
                    if (user!!.id != currentId!!) {
                        mListUser.add(user)
                    }
                    // [END_EXCLUDE]
                }
                getView()?.initAdapter(mListUser)
            }
            .onCancelled {
                // Getting Post failed, log a message
                Log.w(MainActivity.TAG, "loadPost:onCancelled", it.toException())
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
            .build()
    }

}