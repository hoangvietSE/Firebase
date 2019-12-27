package com.soict.hoangviet.firebase.data.sharepreference

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.utils.AppConstant

class AppSharePreference private constructor(var context: Context?) : SharePreference {
    companion object {
        private var instances: AppSharePreference? = null
        fun getInstance(context: Context): AppSharePreference {
            if (instances == null) {
                instances = AppSharePreference(context)
            }
            return instances!!
        }

        const val PREF_USER = "pref_user"
    }

    private val mPrefs: SharedPreferences = context?.getSharedPreferences(AppConstant.PREF_NAME, Context.MODE_PRIVATE)!!

    fun setUser(mUser: User) {
        val rawUser = Gson().toJson(mUser)
        setString(PREF_USER, rawUser)
    }

    fun getUser(): User? {
        val rawUser = getString(PREF_USER)
        return Gson().fromJson(rawUser, User::class.java)
    }

    override fun setString(key: String, value: String) {
        mPrefs.edit().putString(key, value).apply()
    }

    override fun getString(key: String): String {
        return mPrefs.getString(key, "")
    }

    override fun setBoolean(key: String, value: Boolean) {
        mPrefs.edit().putBoolean(key, value).apply()
    }

    override fun getBoolean(key: String): Boolean {
        return mPrefs.getBoolean(key, false)
    }

    override fun setArrayListString(arrayName: String, list: ArrayList<String>) {
        val editor = mPrefs.edit()
        editor.putInt("${arrayName}_size", list.size)
        list.forEachIndexed { index, item ->
            editor.putString("${arrayName}_${index}", item)
        }
        editor.apply()
    }

    override fun getArrayListString(arrayName: String): ArrayList<String> {
        val list = arrayListOf<String>()
        for (index in 0..mPrefs.getInt("${arrayName}_size", -1)) {
            list.add(mPrefs.getString("${arrayName}_${index}", ""))
        }
        return list
    }

    override fun onDestroy() {
        if (context != null) {
            context = null
        }
    }

}