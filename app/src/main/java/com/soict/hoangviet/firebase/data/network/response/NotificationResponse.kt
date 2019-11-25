package com.soict.hoangviet.firebase.data.network.response

import com.google.gson.annotations.SerializedName


data class NotificationResponse(

    @field:SerializedName("notification_type")
    val notificationType: Int? = null,

    @field:SerializedName("type")
    val type: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("content")
    val content: String? = null
)