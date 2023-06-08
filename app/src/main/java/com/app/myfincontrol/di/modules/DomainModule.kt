package com.app.myfincontrol.di.modules

import com.app.myfincontrol.data.repositories.BalanceRepository
import com.app.myfincontrol.data.repositories.ProfileRepository
import com.app.myfincontrol.data.sources.dataStore.LoginDataStore
import com.app.myfincontrol.domain.useCases.BalanceUseCase
import com.app.myfincontrol.domain.useCases.LoginUseCase
import com.app.myfincontrol.domain.useCases.ProfileUseCase
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
        profileRepository
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
    fun providerLoginUseCase(
        loginDataStore: LoginDataStore
    ) : LoginUseCase = LoginUseCase(
        loginDataStore
    )
}