package com.app.myfincontrol.di.modules

import android.content.Context
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
import com.app.myfincontrol.presentation.viewModels.HomeViewModel
import com.app.myfincontrol.presentation.viewModels.LoginViewModel
import com.app.myfincontrol.presentation.viewModels.SettingsViewModel
import com.app.myfincontrol.presentation.viewModels.StatisticsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        @ApplicationContext context: Context,
        sessionUseCase: SessionUseCase,
        profileUseCase: ProfileUseCase,
        balanceUseCase: BalanceUseCase,
        transactionUseCase: TransactionUseCase,
        transactionDAO: TransactionDAO,
        dataStore: UserStore
    ): HomeViewModel {
        return HomeViewModel(
            context,
            sessionUseCase,
            profileUseCase,
            balanceUseCase,
            transactionUseCase,
            transactionDAO,
            dataStore
        )
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Singleton
    @Provides
    fun providerLoginViewModel(
        @ApplicationContext context: Context,
        sessionUseCase: SessionUseCase,
        profileUseCase: ProfileUseCase,
        dataStore: UserStore
    ): LoginViewModel {
        return LoginViewModel(context, sessionUseCase, profileUseCase, dataStore)
    }

    @Singleton
    @Provides
    fun providerSettingsViewModel(
        @ApplicationContext context: Context,
        sessionUseCase: SessionUseCase,
        profileUseCase: ProfileUseCase,
        dataStore: UserStore
    ): SettingsViewModel {
        return SettingsViewModel(
            context,
            sessionUseCase,
            profileUseCase,
            dataStore
        )
    }


    @Singleton
    @Provides
    fun providerStatisticsViewModel(
        statisticsUseCase: StatisticsUseCase,
        dataExchangeUseCase: DataExchangeUseCase,
        dataStore: UserStore
    ): StatisticsViewModel = StatisticsViewModel(
        statisticsUseCase, dataExchangeUseCase,
        dataStore
    )

}