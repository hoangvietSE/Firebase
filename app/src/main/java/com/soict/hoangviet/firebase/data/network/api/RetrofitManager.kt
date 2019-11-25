package com.soict.hoangviet.firebase.data.network.api

class RetrofitManager private constructor() : BaseRetrofit() {
    companion object {
        private var instance: RetrofitManager? = null
        fun getInstance(): RetrofitManager {
            if (instance == null) instance = RetrofitManager()
            return instance!!
        }
    }
}