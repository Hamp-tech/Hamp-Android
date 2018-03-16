package com.hamp.di.module

import com.hamp.HampApplication
import dagger.Module

@Module(includes = [(DataModule::class), (ActivityModule::class), (FragmentModule::class),
    (ViewModelModule::class)])
class AppModule(private val app: HampApplication)