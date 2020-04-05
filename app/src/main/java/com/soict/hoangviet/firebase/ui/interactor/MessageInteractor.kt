package com.soict.hoangviet.firebase.ui.interactor

import com.soict.hoangviet.firebase.data.network.request.MessageRequestBody
import com.soict.hoangviet.firebase.data.network.response.MessageNotificationResponse
import io.reactivex.Single

interface MessageInteractor : BaseInterator {
    fun pushNotificationToReceiver(messageRequestBody: MessageRequestBody): Single<MessageNotificationResponse>
}