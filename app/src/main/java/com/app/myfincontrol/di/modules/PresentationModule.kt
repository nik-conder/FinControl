package com.app.myfincontrol.di.modules

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import com.app.myfincontrol.data.sources.database.TransactionDAO
import com.app.myfincontrol.domain.useCases.BalanceUseCase
import com.app.myfincontrol.domain.useCases.ProfileUseCase
import com.app.myfincontrol.domain.useCases.SessionUseCase
import com.app.myfincontrol.domain.useCases.TransactionUseCase
import com.app.myfincontrol.presentation.viewModels.HomeViewModel
import com.app.myfincontrol.presentation.viewModels.LoginViewModel
import com.app.myfincontrol.presentation.viewModels.SettingsViewModel
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
        transactionDAO: TransactionDAO
    ): HomeViewModel {
        return HomeViewModel(context, sessionUseCase, profileUseCase, balanceUseCase, transactionUseCase, transactionDAO)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Singleton
    @Provides
    fun providerLoginViewModel(
        @ApplicationContext context: Context,
        sessionUseCase: SessionUseCase,
        profileUseCase: ProfileUseCase
    ): LoginViewModel {
        return LoginViewModel(context, sessionUseCase, profileUseCase)
    }

    @Singleton
    @Provides
    fun providerSettingsViewModel(
        @ApplicationContext context: Context,
        sessionUseCase: SessionUseCase,
        profileUseCase: ProfileUseCase
    ): SettingsViewModel {
        return SettingsViewModel(
            context,
            sessionUseCase,
            profileUseCase
        )
    }

}