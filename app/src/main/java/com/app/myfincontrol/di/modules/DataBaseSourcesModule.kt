package com.app.myfincontrol.di.modules

import com.app.myfincontrol.data.AppDatabase
import com.app.myfincontrol.data.sources.FeedDataSource
import com.app.myfincontrol.data.sources.database.BalanceDao
import com.app.myfincontrol.data.sources.database.ProfileDao
import com.app.myfincontrol.data.sources.database.SessionDAO
import com.app.myfincontrol.data.sources.database.TransactionDAO
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
    fun providerFeedDataSource(
        transactionDAO: TransactionDAO
    ): FeedDataSource {
        return FeedDataSource(transactionDAO)
    }
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

    @Singleton
    @Provides
    fun providerSessionDAO(appDatabase: AppDatabase): SessionDAO {
        return appDatabase.sessionDao()
    }

    @Singleton
    @Provides
    fun providerTransactionDAO(appDatabase: AppDatabase): TransactionDAO {
        return appDatabase.transactionDao()
    }

}