package com.soict.hoangviet.firebase.di.builder

import com.soict.hoangviet.firebase.ui.module.FriendsFragmentModule
import com.soict.hoangviet.firebase.ui.module.HomeFragmentModule
import com.soict.hoangviet.firebase.ui.module.MainActivityModule
import com.soict.hoangviet.firebase.ui.module.ProfileFragmentModule
import com.soict.hoangviet.firebase.ui.view.impl.FriendsFragment
import com.soict.hoangviet.firebase.ui.view.impl.HomeFragment
import com.soict.hoangviet.firebase.ui.view.impl.MainActivity
import com.soict.hoangviet.firebase.ui.view.impl.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector(modules = [(FriendsFragmentModule::class)])
    abstract fun bindFriendsFragment(): FriendsFragment

    @ContributesAndroidInjector(modules = [(HomeFragmentModule::class)])
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [(ProfileFragmentModule::class)])
    abstract fun bindProfileFragment(): ProfileFragment

}