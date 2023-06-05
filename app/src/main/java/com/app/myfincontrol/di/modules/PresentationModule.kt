package com.app.myfincontrol.di.modules

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import com.app.myfincontrol.domain.useCases.BalanceUseCase
import com.app.myfincontrol.domain.useCases.ProfileUseCase
import com.app.myfincontrol.presentation.viewModels.HomeViewModel
import com.app.myfincontrol.presentation.viewModels.LoginViewModel
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
    ): HomeViewModel {
        return HomeViewModel(context)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Singleton
    @Provides
    fun providerLoginViewModel(
        @ApplicationContext context: Context,
        profileUseCase: ProfileUseCase,
        balanceUseCase: BalanceUseCase
    ): LoginViewModel {
        return LoginViewModel(context, profileUseCase, balanceUseCase)
    }

}