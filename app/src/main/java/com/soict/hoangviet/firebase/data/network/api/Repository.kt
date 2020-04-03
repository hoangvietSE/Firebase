package com.soict.hoangviet.firebase.data.network.api

import com.soict.hoangviet.firebase.data.network.ApiConstant
import com.soict.hoangviet.firebase.data.network.ApiService
import com.soict.hoangviet.firebase.data.network.request.MessageRequestBody
import com.soict.hoangviet.firebase.data.network.response.MessageNotificationResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(val apiService: ApiService) {
    fun pushNotificationMessage(messageRequestBody: MessageRequestBody): Single<MessageNotificationResponse> {
        return apiService.pushNotification(
            ApiConstant.BaseUrl.FCM,
            "key=AIzaSyCpKjaNbuoIDkNWcwZaUyV6VysA4EOrrhM",
            messageRequestBody
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}