package com.soict.hoangviet.firebase.ui.view.impl;

import androidx.core.content.ContextCompat
import com.soict.hoangviet.baseproject.extension.observableFromView
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.data.local.entity.NotificationSetting
import com.soict.hoangviet.firebase.ui.presenter.NotificationPresenter
import com.soict.hoangviet.firebase.ui.view.NotificationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_notification.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationActivity : BaseActivity(), NotificationView {

    override val mLayoutRes: Int
        get() = R.layout.activity_notification

    @Inject
    lateinit var mPresenter: NotificationPresenter

    override fun updateNotificationSetting(data: NotificationSetting?) {
        switch_notification.isChecked = data?.enable == 1
    }

    override fun initView() {
        mPresenter.onAttach(this)
        fetchData()
    }

    private fun fetchData() {
        mPresenter.fetchData()
    }

    override fun initListener() {
        toolbar.setOnBackClickListener {
            finish()
        }
        switch_notification.observableFromView {
            switch_notification.isChecked = it
                    when {
                        it -> {
                            bg_switch.setBackgroundColor(
                                ContextCompat.getColor(
                                    this,
                                    R.color.md_green_50
                                )
                            )
                        }
                        else -> {
                            bg_switch.setBackgroundColor(
                                ContextCompat.getColor(
                                    this,
                                    R.color.md_white_1000
                                )
                            )
                        }
                    }
        }
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                mPresenter.setNotificationStatus(it)
            }

    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }
}
