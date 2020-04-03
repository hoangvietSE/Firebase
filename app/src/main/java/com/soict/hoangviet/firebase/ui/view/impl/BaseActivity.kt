package com.soict.hoangviet.firebase.ui.view.impl

import android.content.Intent
import android.os.Bundle
import com.google.firebase.messaging.FirebaseMessaging
import com.soict.hoangviet.firebase.common.BaseLoadingDialog
import com.soict.hoangviet.firebase.ui.view.BaseView
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity(), BaseView,
    BaseFragment.CallBack {
    protected open val mLayoutRes: Int? = null
    private var mBaseLoadingDialog: BaseLoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLayoutRes?.let {
            setContentView(it)
        }
        mBaseLoadingDialog = BaseLoadingDialog.getInstance(this)
        initView()
        initListener()
    }



    override fun onDestroy() {
        super.onDestroy()
        unsubscribeTopic()
    }

    private fun unsubscribeTopic() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("topic")
    }

    override fun showLoading() {
        mBaseLoadingDialog?.let { it.showLoadingDialog() }
    }

    override fun hideLoading() {
        mBaseLoadingDialog?.let { it.hideLoadingDialog() }
    }

    protected fun startActivity(classOfT: Class<*>) {
        startActivity(Intent(this, classOfT))
    }

    protected fun startActivityAndClearTask(classOfT: Class<*>) {
        startActivity(Intent(this, classOfT).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        })
    }

}