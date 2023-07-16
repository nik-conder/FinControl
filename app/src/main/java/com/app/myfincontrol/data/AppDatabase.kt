package com.app.myfincontrol.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.myfincontrol.data.converters.BigDecimalConverter
import com.app.myfincontrol.data.converters.DarkModeConverter
import com.app.myfincontrol.data.converters.TimestampConverter
import com.app.myfincontrol.data.converters.TransactionConverter
import com.app.myfincontrol.data.entities.Balance
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.entities.Session
import com.app.myfincontrol.data.entities.Settings
import com.app.myfincontrol.data.entities.Transaction
import com.app.myfincontrol.data.sources.database.BalanceDao
import com.app.myfincontrol.data.sources.database.ProfileDao
import com.app.myfincontrol.data.sources.database.SessionDAO
import com.app.myfincontrol.data.sources.database.SettingsDAO
import com.app.myfincontrol.data.sources.database.TransactionDAO

@Database(
    entities = [
        Profile::class,
        Balance::class,
        Session::class,
        Transaction::class,
        Settings::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(
    BigDecimalConverter::class,
    TimestampConverter::class,
    TransactionConverter::class,
    DarkModeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun balanceDao(): BalanceDao
    abstract fun sessionDao(): SessionDAO
    abstract fun transactionDao(): TransactionDAO
    abstract fun settingsDao(): SettingsDAO
}

//val MIGRATE_1_2 = object : Migration(1,2) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL("ALTER TABLE Settings ADD btnSaveResult INTEGER NOT NULL DEFAULT 0")
//    }
//}
