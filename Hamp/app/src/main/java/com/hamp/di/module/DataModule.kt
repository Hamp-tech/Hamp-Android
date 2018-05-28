package com.hamp.di.module

import com.hamp.HampApplication
import com.hamp.api.RestApi
import com.hamp.db.HampDatabase
import com.hamp.db.HampDatabase.Companion.retrieveDB
import com.hamp.preferences.PreferencesManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun providePreferences(app: HampApplication): PreferencesManager = PreferencesManager(app)

    @Singleton
    @Provides
    fun provideApi() = RestApi()

    @Singleton
    @Provides
    fun provideDB(app: HampApplication) = retrieveDB(app)

    @Singleton
    @Provides
    fun provideUserDao(db: HampDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideServiceQuantityDao(db: HampDatabase) = db.serviceQuantityDao()
}