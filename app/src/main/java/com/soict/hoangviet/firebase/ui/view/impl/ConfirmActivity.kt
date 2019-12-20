package com.soict.hoangviet.firebase.ui.view.impl

import android.os.CountDownTimer
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.extension.visible
import com.soict.hoangviet.firebase.ui.interactor.impl.ConfirmInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.ConfirmPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.ConfirmPresenterImpl
import com.soict.hoangviet.firebase.ui.view.ConfirmView
import kotlinx.android.synthetic.main.activity_confirm.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.*

class ConfirmActivity : BaseActivity<ConfirmPresenter>(), ConfirmView {
    companion object {
        const val TIME_LEFT_IN_MILLS = 60000L
        const val TIME_INTERVAL = 1000L
    }

    override val mLayoutRes: Int
        get() = R.layout.activity_confirm

    private var permissionSend: Boolean = false
    private var mTimeLeftInMills: Long = 0L
    private var mCountDownTimer: CountDownTimer? = null


    override fun getPresenter(): ConfirmPresenter {
        return ConfirmPresenterImpl(this, ConfirmInteractorImpl())
    }

    override fun initView() {
        setToolbar()
        setDefaultCountDown()
        initCountDownTimer()
    }

    private fun initCountDownTimer() {
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMills, TIME_INTERVAL) {
            override fun onTick(miliUtilFinish: Long) {
                mTimeLeftInMills = miliUtilFinish
                updateTimer()
            }

            override fun onFinish() {
                setPermission(true)
                hideCountDown()
            }
        }.start()
    }

    private fun showCountDown() {
        tv_count_time.visible()
    }

    private fun hideCountDown() {
        tv_count_time.gone()
    }

    private fun updateTimer() {
        val minute = (mTimeLeftInMills / 1000) / 60
        val second = (mTimeLeftInMills / 1000) % 60
        tv_count_time.text = String.format(Locale.getDefault(), "%02d:%02d", minute, second)
    }

    private fun setPermission(isSend: Boolean) {
        permissionSend = isSend
    }

    private fun setDefaultCountDown() {
        mTimeLeftInMills = TIME_LEFT_IN_MILLS
    }

    private fun setToolbar() {
        toolbar.setTitle(resources.getString(R.string.active_otp_title_toolbar))
        toolbar.setIconToolbar(R.drawable.ic_back)
        toolbar.hideMainName()
    }

    override fun initListener() {
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }
}