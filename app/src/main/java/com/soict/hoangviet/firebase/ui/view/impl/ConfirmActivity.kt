package com.soict.hoangviet.firebase.ui.view.impl

import android.os.CountDownTimer
import android.text.Html
import android.text.Spanned
import com.soict.hoangviet.baseproject.extension.toast
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.custom.PhoneSmsBaseActivity
import com.soict.hoangviet.firebase.data.network.request.RegisterRequest
import com.soict.hoangviet.firebase.extension.gone
import com.soict.hoangviet.firebase.extension.visible
import com.soict.hoangviet.firebase.ui.presenter.ConfirmPresenter
import com.soict.hoangviet.firebase.ui.view.ConfirmView
import com.soict.hoangviet.firebase.utils.NumberUtil
import kotlinx.android.synthetic.main.activity_confirm.*
import java.util.*
import javax.inject.Inject

class ConfirmActivity : PhoneSmsBaseActivity(), ConfirmView {
    companion object {
        const val EXTRA_REGISTER_OBJECT = "extra_register_object"
        const val TIME_LEFT_IN_MILLS = 60000L
        const val TIME_INTERVAL = 1000L
    }

    @Inject
    lateinit var mPresenter: ConfirmPresenter

    override val mLayoutRes: Int
        get() = R.layout.activity_confirm

    private var permissionSend: Boolean = false
    private var mTimeLeftInMills: Long = 0L
    private var mCountDownTimer: CountDownTimer? = null
    private lateinit var registerRequest: RegisterRequest

    override fun initView() {
        setToolbar()
        getDataIntent()
        setDefaultCountDown()
        initCountDownTimer()
    }

    override fun initListener() {
        btn_confirm.setOnClickListener {
            if (mCheckSendSmsSuccess) {
                showLoading()
                mTokenPhoneSms.confirmTokenPhoneSms(edt_otp)
            } else {
                toast(resources.getString(R.string.active_otp_send_code_error))
            }
        }
        imv_resend.setOnClickListener {
            if (permissionSend) {
                setPermission(false)
                setDefaultCountDown()
                initCountDownTimer()
                showCountDown()
                mTokenPhoneSms.resendPhoneNumberVerification(NumberUtil.formatPhoneTokenSms(registerRequest.phoneNumber))
            }
        }
    }

    private fun getDataIntent() {
        registerRequest = intent.getParcelableExtra(EXTRA_REGISTER_OBJECT)
        sendOtpWithPhoneNumber(registerRequest.phoneNumber)
        edt_phone.setText(registerRequest.phoneNumber)
        val otpHint: Spanned? =
                Html.fromHtml("<small><i style=\"color:#ccc\">" + getString(R.string.active_otp_code_hint) + "</i></small>")
        val phoneHint: Spanned? =
                Html.fromHtml("<small><i style=\"color:#ccc\">" + registerRequest.phoneNumber + "</i></small>")
        edt_phone.hint = phoneHint
        edt_otp.hint = otpHint
    }

    private fun sendOtpWithPhoneNumber(phoneNumber: String) {
        mTokenPhoneSms.startPhoneNumberVerification(NumberUtil.formatPhoneTokenSms(phoneNumber))
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

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }

    override fun onVerifySmsSuccess() {
        register(registerRequest)
    }

    override fun onVerifySmsFailed() {
        hideLoading()
        toast(resources.getString(R.string.active_otp_code_error))
    }

    override fun onAuthSuccess() {
        hideLoading()
        toast(resources.getString(R.string.active_otp_success))
        startActivity(LoginActivity::class.java)
        finish()
    }

    override fun onAuthError() {
        hideLoading()
        toast(resources.getString(R.string.register_error))
    }
}