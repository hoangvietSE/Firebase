package com.soict.hoangviet.firebase.ui.view;

import com.soict.hoangviet.firebase.data.local.entity.NotificationSetting

interface NotificationView : BaseView {
    fun updateNotificationSetting(data: NotificationSetting?)
}