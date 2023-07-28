package com.app.myfincontrol.di.modules

import com.app.myfincontrol.data.repositories.ProfileRepository
import com.app.myfincontrol.data.repositories.SessionRepository
import com.app.myfincontrol.data.repositories.TransactionRepository
import com.app.myfincontrol.domain.useCases.BalanceUseCase
import com.app.myfincontrol.domain.useCases.DataExchangeUseCase
import com.app.myfincontrol.domain.useCases.ProfileUseCase
import com.app.myfincontrol.domain.useCases.SessionUseCase
import com.app.myfincontrol.domain.useCases.StatisticsUseCase
import com.app.myfincontrol.domain.useCases.TransactionUseCase
import com.app.myfincontrol.domain.useCases.ValidatorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DomainModule @Inject constructor() {

    @Singleton
    @Provides
    fun providerProfileUseCase(
        profileRepository: ProfileRepository
    ): ProfileUseCase = ProfileUseCase(
        profileRepository,
    )

    @Singleton
    @Provides
    fun providerBalanceUseCase(
        transactionRepository: TransactionRepository
    ): BalanceUseCase = BalanceUseCase(
        transactionRepository
    )

    @Singleton
    @Provides
    fun providerSessionUseCase(
        sessionRepository: SessionRepository
    ): SessionUseCase = SessionUseCase(
        sessionRepository
    )

    @Singleton
    @Provides
    fun providerTransactionUseCase(
        transactionRepository: TransactionRepository
    ): TransactionUseCase = TransactionUseCase(
        transactionRepository
    )

    @Singleton
    @Provides
    fun providerStatisticsUseCase(
        transactionRepository: TransactionRepository
    ): StatisticsUseCase = StatisticsUseCase(
        transactionRepository
    )

    @Singleton
    @Provides
    fun providerDataExchangeUseCase(
        transactionRepository: TransactionRepository
    ): DataExchangeUseCase = DataExchangeUseCase(
        transactionRepository
    )

    @Singleton
    @Provides
    fun providerValidatorUseCase(): ValidatorUseCase = ValidatorUseCase()
}