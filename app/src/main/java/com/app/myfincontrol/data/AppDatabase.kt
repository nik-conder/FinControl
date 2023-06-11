package com.app.myfincontrol.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.myfincontrol.data.converters.BigDecimalConverter
import com.app.myfincontrol.data.converters.TimestampConverter
import com.app.myfincontrol.data.converters.TransactionConverter
import com.app.myfincontrol.data.entities.Balance
import com.app.myfincontrol.data.entities.Profile
import com.app.myfincontrol.data.entities.Session
import com.app.myfincontrol.data.entities.Transactions
import com.app.myfincontrol.data.sources.database.BalanceDao
import com.app.myfincontrol.data.sources.database.ProfileDao
import com.app.myfincontrol.data.sources.database.SessionDAO
import com.app.myfincontrol.data.sources.database.TransactionDAO

@Database(
    entities = [Profile::class, Balance::class, Session::class, Transactions::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(BigDecimalConverter::class, TimestampConverter::class, TransactionConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun balanceDao(): BalanceDao
    abstract fun sessionDao(): SessionDAO
    abstract fun transactionDao(): TransactionDAO
}

//val MIGRATE_5_6 = object : Migration(5,6) {
//    override fun migrate(database: SupportSQLiteDatabase) {
//        database.execSQL("ALTER TABLE Settings ADD btnSaveResult INTEGER NOT NULL DEFAULT 0")
//    }
//}
