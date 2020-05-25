package com.soict.hoangviet.firebase.utils

import android.content.Context
import com.soict.hoangviet.firebase.application.BaseApplication

object LanguageSharePreference {
    private val mSharedPreferences = BaseApplication.instance.getSharedPreferences(
        AppConstant.PREF_DEFAULT,
        Context.MODE_PRIVATE
    )!!

    fun putLanguage(lang: String) {
        val editor = mSharedPreferences.edit()
        editor.putString(AppConstant.SharePreference.LANGUAGE, lang)
        editor.apply()
    }

    fun getLanguage() =
        mSharedPreferences.getString(AppConstant.SharePreference.LANGUAGE, AppConstant.Language.VIETNAMESE)!!
}