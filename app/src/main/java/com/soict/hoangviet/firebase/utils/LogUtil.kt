package com.soict.hoangviet.firebase.utils

import android.util.Log
import java.lang.StringBuilder

object LogUtil {
    private var isDebug: Boolean = false

    fun init(isDebug: Boolean) {
        this.isDebug = isDebug
    }

    /**
     *Logs a debug message
     */
    fun d(objects: Any) {
        if (isDebug) {
            Log.d("myLog", objects.toString())
        }
    }

    fun d(vararg objects: Any) {
        if (isDebug) {
            val stringBuilder = StringBuilder()
            objects.forEachIndexed { index, any ->
                stringBuilder.append(any)
                if (index != objects.size - 1) {
                    stringBuilder.append(" ")
                }
            }
            Log.d("myLog", stringBuilder.toString())
        }
    }

    /**
     * Logs an error message
     */
    fun e(objects: Any) {
        if (isDebug) {
            Log.e("myLog", objects.toString())
        }
    }

    fun e(vararg objects: Any) {
        if (isDebug) {
            val stringBuilder = StringBuilder()
            objects.forEachIndexed { index, any ->
                stringBuilder.append(any)
                if (index != objects.size - 1) {
                    stringBuilder.append(" ")
                }
            }
            Log.e("myLog", stringBuilder.toString())
        }
    }

    /**
     * Logs an information message
     */
    fun i(objects: Any) {
        if (isDebug) {
            Log.i("myLog", objects.toString())
        }
    }

    fun i(vararg objects: Any) {
        if (isDebug) {
            val stringBuilder = StringBuilder()
            objects.forEachIndexed { index, any ->
                stringBuilder.append(any)
                if (index != objects.size - 1) {
                    stringBuilder.append(" ")
                }
            }
            Log.i("myLog", stringBuilder.toString())
        }
    }

    /**
     * Logs a warning message
     */
    fun w(objects: Any) {
        if (isDebug) {
            Log.w("myLog", objects.toString())
        }
    }

    fun w(vararg objects: Any) {
        if (isDebug) {
            val stringBuilder = StringBuilder()
            objects.forEachIndexed { index, any ->
                stringBuilder.append(any)
                if (index != objects.size - 1) {
                    stringBuilder.append(" ")
                }
            }
            Log.w("myLog", stringBuilder.toString())
        }
    }

    /**
     * Logs a verbose message
     */
    fun v(objects: Any) {
        if (isDebug) {
            Log.v("myLog", objects.toString())
        }
    }

    fun v(vararg objects: Any) {
        if (isDebug) {
            val stringBuilder = StringBuilder()
            objects.forEachIndexed { index, any ->
                stringBuilder.append(any)
                if (index != objects.size - 1) {
                    stringBuilder.append(" ")
                }
            }
            Log.v("myLog", stringBuilder.toString())
        }
    }

}