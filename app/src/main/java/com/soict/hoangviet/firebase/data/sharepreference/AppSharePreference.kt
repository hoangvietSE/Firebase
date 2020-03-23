package com.soict.hoangviet.baseproject.data.sharepreference

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.soict.hoangviet.firebase.utils.AppConstant

class AppSharePreference constructor(var context: Context?) : SharePreference {

    private val mSharedPreferences: SharedPreferences
        get() = context?.getSharedPreferences(AppConstant.PREF_NAME, Context.MODE_PRIVATE)!!
    private val mGson = Gson()

    override fun <T> put(key: String, value: T) {
        val editor = mSharedPreferences.edit()
        when (value) {
            is Boolean -> editor.putBoolean(key, value)
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Long -> editor.putLong(key, value)
            is Float -> editor.putFloat(key, value)
            else -> put(key, toJsonFromObject(value))
        }
        editor.apply()
    }

    override fun <T> get(key: String, clazz: Class<T>): T {
        return when (clazz) {
            Boolean::class.java -> mSharedPreferences.getBoolean(key, false) as T
            String::class.java -> mSharedPreferences.getString(key, "") as T
            Int::class.java -> mSharedPreferences.getInt(key, -1) as T
            Long::class.java -> mSharedPreferences.getLong(key, -1) as T
            Float::class.java -> mSharedPreferences.getFloat(key, -1.0f) as T
            else -> toGsonFromJson(get(key, String::class.java), clazz)
        }
    }

    private fun <T> toJsonFromObject(value: T): String {
        return mGson.toJson(value)
    }

    private fun <T> toGsonFromJson(value: String, clazz: Class<T>): T {
        return mGson.fromJson(value, clazz)
    }

    override fun setArrayListString(arrayName: String, list: ArrayList<String>) {
        put("${arrayName}_size", list.size)
        list.forEachIndexed { index, item ->
            put("${arrayName}_${index}", item)
        }
    }

    override fun getArrayListString(arrayName: String): ArrayList<String> {
        val list = arrayListOf<String>()
        for (index in 0..get("${arrayName}_size", Int::class.java)) {
            list.add(get("${arrayName}_${index}", String::class.java))
        }
        return list
    }

    override fun onDestroy() {
        if (context != null) {
            context = null
        }
    }

}