package com.soict.hoangviet.firebase.ui.view.impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.firebase.messaging.FirebaseMessaging
import com.soict.hoangviet.firebase.common.BaseLoadingDialog
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import com.soict.hoangviet.firebase.ui.view.BaseView
import com.soict.hoangviet.firebase.utils.AppConstant
import com.soict.hoangviet.firebase.utils.LanguageSharePreference
import com.soict.hoangviet.firebase.utils.LanguageUtil
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity(), BaseView,
    BaseFragment.CallBack {
    protected open val mLayoutRes: Int? = null
    private lateinit var mBaseLoadingDialog: BaseLoadingDialog
    @Inject
    lateinit var mSharedPreferences: SharePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLayoutRes?.let {
            setContentView(it)
        }
        val lang = LanguageSharePreference.getLanguage()
        LanguageUtil.setCurrentLanguage(this, lang)
        mBaseLoadingDialog = BaseLoadingDialog.getInstance(this)
        initView()
        initListener()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LanguageUtil.setCurrentLanguage(newBase!!, LanguageSharePreference.getLanguage()))
    }

    override fun onDestroy() {
        super.onDestroy()
        unsubscribeTopic()
    }

    private fun unsubscribeTopic() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("topic")
    }

    override fun showLoading() {
        mBaseLoadingDialog.let {
            it.showLoadingDialog()
        }
    }

    override fun hideLoading() {
        mBaseLoadingDialog.let {
            it.hideLoadingDialog()
        }
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