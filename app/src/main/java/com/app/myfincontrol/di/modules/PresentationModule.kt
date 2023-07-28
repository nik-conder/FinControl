package com.app.myfincontrol.di.modules

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import com.app.myfincontrol.data.sources.UserStore
import com.app.myfincontrol.data.sources.database.TransactionDAO
import com.app.myfincontrol.domain.useCases.BalanceUseCase
import com.app.myfincontrol.domain.useCases.DataExchangeUseCase
import com.app.myfincontrol.domain.useCases.ProfileUseCase
import com.app.myfincontrol.domain.useCases.SessionUseCase
import com.app.myfincontrol.domain.useCases.StatisticsUseCase
import com.app.myfincontrol.domain.useCases.TransactionUseCase
import com.app.myfincontrol.domain.useCases.ValidatorUseCase
import com.app.myfincontrol.presentation.viewModels.HomeViewModel
import com.app.myfincontrol.presentation.viewModels.LoginViewModel
import com.app.myfincontrol.presentation.viewModels.SettingsViewModel
import com.app.myfincontrol.presentation.viewModels.StatisticsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PresentationModule @Inject constructor() {

    @Singleton
    @Provides
    fun provideSavedStateHandle(): SavedStateHandle = SavedStateHandle()

    @Singleton
    @Provides
    fun providerHomeViewModel(
        profileUseCase: ProfileUseCase,
        balanceUseCase: BalanceUseCase,
        transactionUseCase: TransactionUseCase,
        transactionDAO: TransactionDAO,
    ): HomeViewModel {
        return HomeViewModel(
            profileUseCase,
            balanceUseCase,
            transactionUseCase,
            transactionDAO,
        )
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Singleton
    @Provides
    fun providerLoginViewModel(
        sessionUseCase: SessionUseCase,
        profileUseCase: ProfileUseCase,
        validatorUseCase: ValidatorUseCase
    ): LoginViewModel {
        return LoginViewModel(sessionUseCase, profileUseCase, validatorUseCase)
    }

    @Singleton
    @Provides
    fun providerSettingsViewModel(
        sessionUseCase: SessionUseCase,
        profileUseCase: ProfileUseCase,
    ): SettingsViewModel {
        return SettingsViewModel(
            sessionUseCase,
            profileUseCase
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun providerStatisticsViewModel(
        statisticsUseCase: StatisticsUseCase,
        dataExchangeUseCase: DataExchangeUseCase
    ): StatisticsViewModel = StatisticsViewModel(
        statisticsUseCase,
        dataExchangeUseCase,
    )

}