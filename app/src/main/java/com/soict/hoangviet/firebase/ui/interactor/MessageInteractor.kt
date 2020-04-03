package com.soict.hoangviet.firebase.ui.interactor

import com.soict.hoangviet.firebase.data.network.response.MessageNotificationResponse
import io.reactivex.Single

interface MessageInteractor : BaseInterator {
    fun pushNotificationToReceiver(receiver: String, receiverToken: String) : Single<MessageNotificationResponse>
}