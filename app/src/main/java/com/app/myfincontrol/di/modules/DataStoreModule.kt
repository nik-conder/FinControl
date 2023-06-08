package com.app.myfincontrol.di.modules

import android.content.Context
import com.app.myfincontrol.data.sources.dataStore.LoginDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DataStoreModule @Inject constructor() {

    @Singleton
    @Provides
    fun providerLoginDataStore(@ApplicationContext context: Context): LoginDataStore {
        return LoginDataStore(context)
    }

}