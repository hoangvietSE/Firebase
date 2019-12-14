package com.soict.hoangviet.firebase.ui.view.impl

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.soict.hoangviet.firebase.utils.DeviceUtil
import com.soict.hoangviet.firebase.utils.DialogUtil

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    companion object {
        const val SPLASH_TIME = 1500L
    }

    private fun initView() {
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
            startActivity(Intent(this, LoginActivity::class.java).apply {
            })
            finish()
        } else {
            startActivity(Intent(this, LoginActivity::class.java).apply {
            })
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
}