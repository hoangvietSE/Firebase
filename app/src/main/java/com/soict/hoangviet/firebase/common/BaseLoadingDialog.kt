package com.soict.hoangviet.firebase.common

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.soict.hoangviet.firebase.R

class BaseLoadingDialog private constructor(private val context: Context) {

    private var mDialog: Dialog? = null
    private var shown: Boolean = false

    //Before initialize instance of class
    //Before class constructor
    companion object {
        private var instance: BaseLoadingDialog? = null
        fun getInstance(context: Context): BaseLoadingDialog {
            instance = BaseLoadingDialog(context)
            return instance!!
        }
    }

    //After primary constructor
    init {
        mDialog = Dialog(context)
        mDialog?.apply {
            setContentView(R.layout.layout_loading)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    fun showLoadingDialog() {
        if (!(context as Activity).isFinishing) {
            if (!shown && mDialog != null) {
                force()
                mDialog?.show()
            }
        }

    }

    fun hideLoadingDialog() {
        if (shown && (mDialog?.isShowing!!) && mDialog != null) {
            initialization()
            mDialog?.dismiss()
        }
    }

    private fun force() {
        shown = true
    }

    private fun initialization() {
        shown = false
    }
}