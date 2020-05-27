package com.soict.hoangviet.firebase.ui.view.impl

import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.soict.hoangviet.firebase.broadcast.AppBroadcast
import com.soict.hoangviet.firebase.common.BaseLoadingDialog
import com.soict.hoangviet.firebase.extension.inflate
import com.soict.hoangviet.firebase.ui.view.BaseView
import com.soict.hoangviet.firebase.utils.LanguageUtil
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseFragment : Fragment(), BaseView {
    protected var parentActivity: DaggerAppCompatActivity? = null
    abstract val mLayoutRes: Int
    private var broadcast: AppBroadcast? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            parentActivity = context
        }
        (parentActivity as BaseActivity).onFragmentAttached()
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return parentActivity?.inflate(mLayoutRes, container!!, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    override fun onResume() {
        super.onResume()
        broadcast = AppBroadcast()
        requireActivity().registerReceiver(broadcast, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(broadcast)
    }

    override fun showLoading() {
        parentActivity?.let {
            BaseLoadingDialog.getInstance(it).showLoadingDialog()
        }
    }

    override fun hideLoading() {
        parentActivity?.let {
            BaseLoadingDialog.getInstance(it).hideLoadingDialog()
        }
    }

    override fun onDetach() {
        super.onDetach()
        (parentActivity as BaseActivity).onFragmentDetached("")

    }

    interface CallBack {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }
}