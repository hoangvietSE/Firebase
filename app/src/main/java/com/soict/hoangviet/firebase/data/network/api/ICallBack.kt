package com.soict.hoangviet.firebase.data.network

import com.soict.hoangviet.firebase.data.network.api.ApiException

interface ICallBack<T> {
    fun onSuccess(result: T)
    fun onError(e: ApiException)
}