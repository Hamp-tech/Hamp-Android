package com.hamp.di.module

import com.hamp.HampApplication
import com.hamp.api.RestApi
import com.hamp.preferences.PreferencesManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun provideApi() = RestApi()

    @Singleton
    @Provides
    fun providePreferences(app: HampApplication): PreferencesManager = PreferencesManager(app)
}