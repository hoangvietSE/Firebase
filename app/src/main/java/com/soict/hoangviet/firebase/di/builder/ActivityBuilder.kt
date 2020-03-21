package com.soict.hoangviet.firebase.di.builder

import com.soict.hoangviet.firebase.ui.module.MainActivityModule
import com.soict.hoangviet.firebase.ui.view.impl.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun bindMainActivity(): MainActivity

}