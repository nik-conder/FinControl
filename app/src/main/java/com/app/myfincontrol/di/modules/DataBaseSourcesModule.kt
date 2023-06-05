package com.app.myfincontrol.di.modules

import com.app.myfincontrol.data.AppDatabase
import com.app.myfincontrol.data.sources.database.DAO.BalanceDao
import com.app.myfincontrol.data.sources.database.DAO.ProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataBaseSourcesModule @Inject constructor() {

    @Singleton
    @Provides
    fun providerProfileDAO(appDatabase: AppDatabase): ProfileDao {
        return appDatabase.profileDao()
    }

    @Singleton
    @Provides
    fun providerBalanceDAO(appDatabase: AppDatabase): BalanceDao {
        return appDatabase.balanceDao()
    }

}