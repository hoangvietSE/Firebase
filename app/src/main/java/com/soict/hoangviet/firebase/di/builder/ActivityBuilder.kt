package com.soict.hoangviet.firebase.di.builder

import com.soict.hoangviet.firebase.ui.module.*
import com.soict.hoangviet.firebase.ui.view.impl.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [(ConfirmActivityModule::class)])
    abstract fun bindConfirmActivity(): ConfirmActivity

    @ContributesAndroidInjector(modules = [(DynamicLinkActivityModule::class)])
    abstract fun bindDynamicLinkActivity(): DynamicLinkActivity

    @ContributesAndroidInjector(modules = [(LoginActivityModule::class)])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [(MessageActivityModule::class)])
    abstract fun bindMessageActivity(): MessageActivity

    @ContributesAndroidInjector(modules = [(RegisterActivityModule::class)])
    abstract fun bindRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector(modules = [(SplashActivityModule::class)])
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [(UpdateProfileActivityModule::class)])
    abstract fun bindUpdateProfileActivity(): UpdateProfileActivity

    @ContributesAndroidInjector(modules = [(InfoActivityModule::class)])
    abstract fun bindInfoActivity(): InfoActivity

    @ContributesAndroidInjector(modules = [(TutorialActivityModule::class)])
    abstract fun bindTutorialActivity(): TutorialActivity

    @ContributesAndroidInjector(modules = [(NotificationActivityModule::class)])
    abstract fun bindNotificationActivity(): NotificationActivity

    @ContributesAndroidInjector(modules = [(ChangePasswordActivityModule::class)])
    abstract fun bindChangePasswordActivity(): ChangePasswordActivity

    @ContributesAndroidInjector(modules = [(FaqActivityModule::class)])
    abstract fun bindFaqActivity(): FaqActivity

}