package com.soict.hoangviet.firebase.di.component

import android.app.Application
import android.content.Context
import com.soict.hoangviet.firebase.application.BaseApplication
import com.soict.hoangviet.firebase.di.builder.ActivityBuilder
import com.soict.hoangviet.firebase.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        (AndroidInjectionModule::class),
        (AppModule::class),
        (ActivityBuilder::class)]
)
@Singleton
interface AppComponent {

    //Declare for component dependences in use
    fun getContext(): Context

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(baseApplication: BaseApplication)

}