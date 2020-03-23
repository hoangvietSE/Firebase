package com.soict.hoangviet.firebase.di.module

import android.app.Application
import android.content.Context
import com.soict.hoangviet.baseproject.data.sharepreference.AppSharePreference
import com.soict.hoangviet.firebase.data.sharepreference.SharePreference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    internal fun provideSharePreference(context: Context): SharePreference =
        AppSharePreference(context)

}