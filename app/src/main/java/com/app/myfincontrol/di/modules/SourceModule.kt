package com.app.myfincontrol.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SourceModule  @Inject constructor() {
    @Singleton
    @Provides
    fun providesDataBaseSourcesModule(): DataBaseSourcesModule = DataBaseSourcesModule()

    @Singleton
    @Provides
    fun providesNetworkSourcesModule(): NetworkSourcesModule = NetworkSourcesModule()
}