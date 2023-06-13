package com.app.myfincontrol.di.modules

import com.app.myfincontrol.data.repositories.BalanceRepository
import com.app.myfincontrol.data.repositories.ProfileRepository
import com.app.myfincontrol.data.repositories.SessionRepository
import com.app.myfincontrol.data.repositories.TransactionRepository
import com.app.myfincontrol.data.sources.FeedDataSource
import com.app.myfincontrol.data.sources.FeedDataSourceMediator
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
class DataModule @Inject constructor() {

//    @Singleton
//    @Provides
//    fun providerFeedPagingSource(
//        transactionDAO: TransactionDAO
//    ): FeedDataSource = FeedDataSource(transactionDAO)

    @Singleton
    @Provides
    fun providesProfileRepository(
        profileDao: ProfileDao
    ): ProfileRepository = ProfileRepository(
        profileDao
    )

    @Singleton
    @Provides
    fun providerBalanceRepository(
        balanceDao: BalanceDao
    ): BalanceRepository = BalanceRepository(
        balanceDao
    )

    @Singleton
    @Provides
    fun providerSessionRepository(
        sessionDAO: SessionDAO
    ): SessionRepository = SessionRepository(
        sessionDAO
    )

    @Singleton
    @Provides
    fun providerTransactionRepository(
        transactionDAO: TransactionDAO,
        feedDataSource: FeedDataSource
    ): TransactionRepository = TransactionRepository(
        transactionDAO, feedDataSource
    )
}