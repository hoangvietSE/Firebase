package com.soict.hoangviet.firebase.data.sharepreference

import com.soict.hoangviet.firebase.utils.AppConstant

interface SharePreference {
    fun <T> put(key: String, value: T)
    fun <T> get(key: String, clazz: Class<T>): T
    fun clearAllPreference()
    fun setArrayListString(arrayName: String, list: ArrayList<String>)
    fun getArrayListString(arrayName: String): ArrayList<String>
    fun onDestroy()
}