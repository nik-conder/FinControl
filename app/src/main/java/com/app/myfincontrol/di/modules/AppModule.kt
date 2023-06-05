package com.app.myfincontrol.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module( includes = [PresentationModule::class, RoomModule::class, DataModule::class, DomainModule::class] )
class AppModule @Inject constructor() {

    @Singleton
    @Provides
    fun providerPresentationModule(): PresentationModule = PresentationModule()

    @Singleton
    @Provides
    fun providerRoomModule(): RoomModule = RoomModule()

    @Singleton
    @Provides
    fun providerDataModule(): DataModule = DataModule()

    @Singleton
    @Provides
    fun providerDomainModule(): DomainModule = DomainModule()

}