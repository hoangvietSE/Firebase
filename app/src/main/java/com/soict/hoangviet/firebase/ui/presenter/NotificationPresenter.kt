package com.soict.hoangviet.firebase.ui.presenter;

import com.soict.hoangviet.firebase.ui.view.NotificationView;
import com.soict.hoangviet.firebase.ui.interactor.NotificationInteractor;

interface NotificationPresenter : BasePresenter<NotificationView, NotificationInteractor> {
    fun setNotificationStatus(checked: Boolean)
    fun fetchData()
}