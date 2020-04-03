package com.soict.hoangviet.firebase.data.network.request

import com.google.gson.annotations.SerializedName

data class MessageRequestBody(

	@field:SerializedName("data")
	val data: DataNotification? = null,

	@field:SerializedName("to")
	val to: String? = null
)