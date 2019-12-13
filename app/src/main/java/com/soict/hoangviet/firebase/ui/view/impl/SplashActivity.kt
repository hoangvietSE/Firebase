package com.soict.hoangviet.firebase.ui.view.impl

import android.content.DialogInterface
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.soict.hoangviet.firebase.R
import com.soict.hoangviet.firebase.module.GlideApp
import com.soict.hoangviet.firebase.ui.interactor.impl.SplashInteractorImpl
import com.soict.hoangviet.firebase.ui.presenter.SplashPresenter
import com.soict.hoangviet.firebase.ui.presenter.impl.SplashPresenterImpl
import com.soict.hoangviet.firebase.ui.view.SplashView
import com.soict.hoangviet.firebase.utils.DeviceUtil
import com.soict.hoangviet.firebase.utils.DialogUtil
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity<SplashPresenter>(), SplashView {
    override val mLayoutRes: Int
        get() = R.layout.activity_splash

    companion object {
        const val SPLASH_TIME = 2000L
    }

    override fun getPresenter(): SplashPresenter {
        return SplashPresenterImpl(this, SplashInteractorImpl())
    }

    override fun onStart() {
        super.onStart()
    }

    override fun initView() {
        GlideApp.with(this)
                .load(R.drawable.splash)
                .into(imv_splash)
        Handler().postDelayed({
            checkNetworkConnection()
        }, SPLASH_TIME)
    }

    private fun checkNetworkConnection() {
        if (DeviceUtil.isConnectedToNetwork(this)) {
            checkCurrentUser()
        } else {
            showAlertNoNetworkConnection()
        }
    }

    private fun checkCurrentUser() {
        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(HomeActivity::class.java)
            finish()
        } else {
            startActivity(MainActivity::class.java)
            finish()
        }
    }

    private fun showAlertNoNetworkConnection() {
        DialogUtil.showMessageDialog(
                this,
                "Warning!",
                "Không có kết nối Internet. Vui lòng quay lại khi có kết nối!",
                false,
                "Đồng ý",
                object : DialogUtil.BaseDialogInterface {
                    override fun onPositiveClick(dialog: DialogInterface) {
                        finish()
                    }

                    override fun onNegativeClick(dialog: DialogInterface) {
                    }

                    override fun onItemClick(dialog: DialogInterface, position: Int) {
                    }
                }
        )
    }

    override fun initListener() {
    }

    override fun onFragmentAttached() {
    }

    override fun onFragmentDetached(tag: String) {
    }
}