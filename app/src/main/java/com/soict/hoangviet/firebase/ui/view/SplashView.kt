package com.soict.hoangviet.firebase.ui.view

interface SplashView : BaseView {
    fun goToHomeScreen()
    fun showError()
    fun goToTutorialScreen()
    fun checkCurrentUser()
}