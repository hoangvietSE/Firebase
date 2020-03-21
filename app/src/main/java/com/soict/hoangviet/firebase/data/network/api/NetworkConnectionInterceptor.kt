package com.soict.hoangviet.firebase.data.network.api

import android.content.Context
import android.net.ConnectivityManager
import com.soict.hoangviet.baseproject.extension.hasNetworkConnection
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkConnectionInterceptor(val mContext: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!mContext.hasNetworkConnection()) {
            throw NoConnectivityException()
        }
        val requestBuilder = chain.request().newBuilder()
        return chain.proceed(requestBuilder.build())
    }

    class NoConnectivityException : IOException() {
        override val message: String?
            get() = "Không có kết nối Internet"
    }
}