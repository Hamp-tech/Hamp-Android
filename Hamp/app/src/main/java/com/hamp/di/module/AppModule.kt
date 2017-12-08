package com.hamp.di.module

import android.app.Application
import dagger.Module

@Module(includes = arrayOf(
        DataModule::class,
        ActivityModule::class,
        ViewModelModule::class
))
class AppModule(private val app: Application)