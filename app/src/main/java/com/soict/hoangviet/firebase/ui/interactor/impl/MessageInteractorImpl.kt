package com.soict.hoangviet.firebase.ui.interactor.impl

import com.soict.hoangviet.firebase.data.network.api.Repository
import com.soict.hoangviet.firebase.data.network.request.MessageRequestBody
import com.soict.hoangviet.firebase.data.network.response.MessageNotificationResponse
import com.soict.hoangviet.firebase.ui.interactor.MessageInteractor
import io.reactivex.Single
import javax.inject.Inject

class MessageInteractorImpl @Inject internal constructor(
    mRepository: Repository
) : BaseInteractorImpl(repository = mRepository), MessageInteractor {
    override fun pushNotificationToReceiver(messageRequestBody: MessageRequestBody): Single<MessageNotificationResponse> {
        return repository.pushNotificationMessage(messageRequestBody)
    }
}