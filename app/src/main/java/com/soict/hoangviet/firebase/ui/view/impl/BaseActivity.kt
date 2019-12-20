package com.soict.hoangviet.firebase.ui.view.impl

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.soict.hoangviet.firebase.common.BaseLoadingDialog
import com.soict.hoangviet.firebase.data.network.response.User
import com.soict.hoangviet.firebase.ui.presenter.BasePresenter
import com.soict.hoangviet.firebase.ui.view.BaseView

abstract class BaseActivity<P : BasePresenter> : AppCompatActivity(), BaseView, BaseFragment.CallBack {
    protected abstract val mLayoutRes: Int
    protected val mPresenter: P get() = getPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mLayoutRes)
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