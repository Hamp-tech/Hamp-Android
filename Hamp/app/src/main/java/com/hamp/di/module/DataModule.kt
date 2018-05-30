package com.hamp.di.module

import com.hamp.HampApplication
import com.hamp.data.api.RestApi
import com.hamp.data.db.HampDatabase
import com.hamp.data.db.HampDatabase.Companion.retrieveDB
import com.hamp.data.preferences.PreferencesManager
import com.hamp.repository.*
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

    @Provides
    fun provideUserRepository(repository: UserRepositoryImpl): UserRepository = repository

    @Provides
    fun provideCardRepository(repository: CardRepositoryImpl): CardRepository = repository

    @Provides
    fun provideServiceRepository(repository: ServiceRepositoryImpl): ServiceRepository = repository

    @Provides
    fun provideTransactionRepository(repository: TransactionRepositoryImpl): TransactionRepository = repository
}