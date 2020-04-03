package com.soict.hoangviet.firebase.ui.interactor.impl

import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.application.BaseApplication
import com.soict.hoangviet.firebase.data.network.api.Repository
import com.soict.hoangviet.firebase.data.network.request.DataNotification
import com.soict.hoangviet.firebase.data.network.request.MessageRequestBody
import com.soict.hoangviet.firebase.data.network.response.MessageNotificationResponse
import com.soict.hoangviet.firebase.ui.interactor.MessageInteractor
import io.reactivex.Single
import javax.inject.Inject

class MessageInteractorImpl @Inject internal constructor(
    mRepository: Repository
) : BaseInteractorImpl(repository = mRepository), MessageInteractor {
    override fun pushNotificationToReceiver(receiver: String, receiverToken: String): Single<MessageNotificationResponse> {
        val messageRequestBody = MessageRequestBody(DataNotification(title = BaseApplication.instance.getString(R.string.message_push_notification_title)), receiverToken)
        return repository.pushNotificationMessage(messageRequestBody)
    }
}