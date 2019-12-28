package com.soict.hoangviet.firebase.ui.view

import com.soict.hoangviet.firebase.data.network.response.HomeResponse

interface HomeView : BaseView {
    fun showAllChatUsers(mListUserChat: ArrayList<HomeResponse>)
    fun notifyChange(position: Int)
}