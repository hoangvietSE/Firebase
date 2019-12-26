package com.soict.hoangviet.firebase.ui.view

import com.soict.hoangviet.firebase.data.network.response.User

interface HomeView : BaseView {
    fun showAllChatUsers(mListUserChat: ArrayList<User>)
}