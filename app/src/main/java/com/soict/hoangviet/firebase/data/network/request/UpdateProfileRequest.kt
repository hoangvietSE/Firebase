package com.soict.hoangviet.firebase.data.network.request

import android.net.Uri

class UpdateProfileRequest {
    var id: String = ""
    var fullname: String = ""
    var email: String = ""
    var phone: String = ""
    var gender: Int = 0
    var birthday: String = ""
    var avatar: Uri? = null
}