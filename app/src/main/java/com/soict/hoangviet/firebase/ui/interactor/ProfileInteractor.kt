package com.soict.hoangviet.firebase.ui.interactor

import com.soict.hoangviet.firebase.data.network.ICallBack
import com.soict.hoangviet.firebase.data.network.response.BaseListEntityResponse
import com.soict.hoangviet.firebase.data.network.response.TestResponse
import io.reactivex.disposables.Disposable

interface ProfileInteractor : BaseInterator {
    fun fetchListDriver(data: MutableMap<String, Any>, callBack: ICallBack<BaseListEntityResponse<TestResponse>>): Disposable
}