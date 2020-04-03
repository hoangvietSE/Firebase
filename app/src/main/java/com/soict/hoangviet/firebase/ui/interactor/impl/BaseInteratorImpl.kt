package com.soict.hoangviet.firebase.ui.interactor.impl

import com.google.gson.Gson
import com.soict.hoangviet.firebase.data.network.api.Repository
import com.soict.hoangviet.firebase.ui.interactor.BaseInterator
import okhttp3.MultipartBody
import okhttp3.RequestBody

open class BaseInteractorImpl
internal constructor(protected var repository: Repository) : BaseInterator {

    protected fun createRequestBody(request: Any): RequestBody {
        var rawString: String? = null
        try {
            rawString = Gson().toJson(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return RequestBody.create(MultipartBody.FORM, rawString ?: "")
    }
}