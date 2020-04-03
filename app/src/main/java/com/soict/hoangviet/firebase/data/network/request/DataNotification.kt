package com.soict.hoangviet.firebase.data.network.request

import com.google.gson.annotations.SerializedName

data class DataNotification(

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("body")
	val body: String? = null
)