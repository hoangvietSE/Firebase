package com.soict.hoangviet.firebase.ui.view.impl

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.soict.hoangviet.firebase.common.BaseLoadingDialog
import com.soict.hoangviet.firebase.ui.presenter.BasePresenter
import com.soict.hoangviet.firebase.ui.view.BaseView

abstract class BaseFragment<P : BasePresenter> : Fragment(), BaseView {
    private var parentActivity: AppCompatActivity? = null
    protected val mPresenter: P get() = getPresenter()

    override
    fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            parentActivity = context
        }
        (parentActivity as BaseActivity<*>)?.onFragmentAttached()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.onAttach()
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
        (parentActivity as BaseActivity<*>).onFragmentDetached("")
    }

    abstract fun getPresenter(): P

    interface CallBack {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }
}