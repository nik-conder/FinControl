package com.app.myfincontrol.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.myfincontrol.data.entities.Balance
import com.app.myfincontrol.data.entities.BigDecimalConverter
import com.app.myfincontrol.data.sources.database.DAO.ProfileDao
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.sources.database.DAO.BalanceDao

@Database(
    entities = [Profile::class, Balance::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(BigDecimalConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun balanceDao(): BalanceDao
}

//val MIGRATE_5_6 = object : Migration(5,6) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL("ALTER TABLE Settings ADD btnSaveResult INTEGER NOT NULL DEFAULT 0")
//    }
//}
