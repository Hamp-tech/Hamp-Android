package com.hamp.di

import com.hamp.HampApplication
import com.hamp.di.module.ActivityModule
import com.hamp.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidInjectionModule::class), (AndroidSupportInjectionModule::class),
    (AppModule::class), (ActivityModule::class)])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: HampApplication): Builder

        fun build(): AppComponent
    }

    fun inject(app: HampApplication)
}