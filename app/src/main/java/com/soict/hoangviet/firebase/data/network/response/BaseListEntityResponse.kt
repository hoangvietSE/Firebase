package com.soict.hoangviet.firebase.data.network.response

import com.google.gson.annotations.SerializedName

class BaseListEntityResponse<T> : BaseResponse() {
    @SerializedName("data")
    var data: ArrayList<T>? = null
}