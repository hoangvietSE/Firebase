package com.soict.hoangviet.firebase.ui.view

import com.soict.hoangviet.firebase.data.network.response.User

interface ProfileView : BaseView {
    fun showUserInfo(user: User)
}