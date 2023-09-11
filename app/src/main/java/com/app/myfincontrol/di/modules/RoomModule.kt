package com.app.myfincontrol.di.modules

import android.content.Context
import androidx.room.Room
import com.app.myfincontrol.data.AppDatabase
import com.app.myfincontrol.data.Configuration
import com.app.myfincontrol.data.MIGRATE_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule @Inject constructor() {

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, Configuration.Database.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .addMigrations(MIGRATE_1_2)
            .allowMainThreadQueries()
            .build()
    }
}