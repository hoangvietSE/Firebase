package com.soict.hoangviet.firebase.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.soict.hoangviet.baseproject.extension.hasNetworkConnection
import com.soict.hoangviet.firebase.eventbus.NetworkEvent
import org.greenrobot.eventbus.EventBus

class AppBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ConnectivityManager.CONNECTIVITY_ACTION -> {
                if (context!!.hasNetworkConnection()) {
                    EventBus.getDefault().postSticky(NetworkEvent(isNetworkConnection = true))
                } else {
                    EventBus.getDefault().postSticky(NetworkEvent(isNetworkConnection = false))
                }
            }
        }
    }
}