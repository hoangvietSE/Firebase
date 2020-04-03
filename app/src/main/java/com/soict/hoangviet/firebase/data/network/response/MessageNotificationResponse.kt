package com.soict.hoangviet.firebase.data.network.response

import com.google.gson.annotations.SerializedName

data class MessageNotificationResponse(

	@field:SerializedName("message_id")
	val messageId: Long? = null
)