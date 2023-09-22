package com.app.myfincontrol.di.modules

import android.content.Context
import com.app.myfincontrol.data.sources.UserStore
import com.app.myfincontrol.data.repositories.ProfileRepository
import com.app.myfincontrol.data.repositories.SessionRepository
import com.app.myfincontrol.data.repositories.TransactionRepository
import com.app.myfincontrol.data.sources.FeedDataSource
import com.app.myfincontrol.data.sources.database.ProfileDao
import com.app.myfincontrol.data.sources.database.SessionDAO
import com.app.myfincontrol.data.sources.database.TransactionDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule @Inject constructor() {
    @Singleton
    @Provides
    fun provideUserStore(
        @ApplicationContext context: Context,
    ): UserStore {
        return UserStore(context)
    }

    @Singleton
    @Provides
    fun providesProfileRepository(
        profileDao: ProfileDao
    ): ProfileRepository = ProfileRepository(
        profileDao
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