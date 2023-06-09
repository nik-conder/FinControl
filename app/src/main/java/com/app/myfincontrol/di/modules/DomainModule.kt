package com.app.myfincontrol.di.modules

import com.app.myfincontrol.data.repositories.BalanceRepository
import com.app.myfincontrol.data.repositories.ProfileRepository
import com.app.myfincontrol.data.repositories.SessionRepository
import com.app.myfincontrol.data.repositories.TransactionRepository
import com.app.myfincontrol.domain.useCases.BalanceUseCase
import com.app.myfincontrol.domain.useCases.ProfileUseCase
import com.app.myfincontrol.domain.useCases.RegistrationUseCase
import com.app.myfincontrol.domain.useCases.SessionUseCase
import com.app.myfincontrol.domain.useCases.TransactionUseCase
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
        balanceRepository: BalanceRepository
    ): BalanceUseCase = BalanceUseCase(
        balanceRepository
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
    fun providerRegistrationUseCase(
        profileRepository: ProfileRepository,
        balanceRepository: BalanceRepository

    ): RegistrationUseCase = RegistrationUseCase(
        profileRepository, balanceRepository
    )

    @Singleton
    @Provides
    fun providerTransactionUseCase(
        balanceRepository: BalanceRepository,
        transactionRepository: TransactionRepository
    ): TransactionUseCase = TransactionUseCase(
        balanceRepository,
        transactionRepository
    )
}