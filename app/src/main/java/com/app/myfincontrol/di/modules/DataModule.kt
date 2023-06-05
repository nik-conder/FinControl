package com.app.myfincontrol.di.modules

import com.app.myfincontrol.data.sources.database.DAO.ProfileDao
import com.app.myfincontrol.data.repositories.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule @Inject constructor() {

    @Singleton
    @Provides
    fun providesProfileRepository(
        profileDao: ProfileDao
    ): ProfileRepository = ProfileRepository(
        profileDao
    )

}