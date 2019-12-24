package com.soict.hoangviet.firebase.ui.view.impl

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.soict.hoangviet.firebase.common.BaseLoadingDialog
import com.soict.hoangviet.firebase.ui.presenter.BasePresenter
import com.soict.hoangviet.firebase.ui.view.BaseView

abstract class BaseActivity<P : BasePresenter> : AppCompatActivity(), BaseView, BaseFragment.CallBack {
    protected open val mLayoutRes: Int? = null
    protected lateinit var mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLayoutRes?.let {
            setContentView(it)
        }
        mPresenter = getPresenter()
        mPresenter.onAttach()
        initListener()
        subscribeTopic()
    }

    private fun subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("topic_app")
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        unsubscribeTopic()
    }

    private fun unsubscribeTopic() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("topic_app")
    }

    override fun showLoading() {
        BaseLoadingDialog.getInstance(this).showLoadingDialog()
    }

    override fun hideLoading() {
        BaseLoadingDialog.getInstance(this).hideLoadingDialog()
    }

    abstract fun getPresenter(): P

    protected fun startActivity(classOfT: Class<*>) {
        startActivity(Intent(this, classOfT))
    }

    protected fun startActivityAndClearTask(classOfT: Class<*>) {
        startActivity(Intent(this, classOfT).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        })
    }

    override fun onStop() {
        super.onStop()
        mPresenter.onDetach()
    }

}