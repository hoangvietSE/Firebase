package com.soict.hoangviet.firebase.ui.view

import com.soict.hoangviet.firebase.data.network.response.User

interface FriendsView : BaseView {
    fun initAdapter(mListUser: ArrayList<User>)
}