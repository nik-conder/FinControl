package com.app.myfincontrol.data.entities

import android.icu.math.BigDecimal
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.app.myfincontrol.data.Currency

@Entity

data class Balance(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "profile_id") val profile_id: Int,
    @ColumnInfo(name = "main_balance") val main_balance: BigDecimal = BigDecimal("0.00")
)

class BigDecimalConverter {
    @TypeConverter
    fun fromString(value: String): BigDecimal {
        return BigDecimal(value)
    }

    @TypeConverter
    fun toString(value: BigDecimal): String {
        return value.toString()
    }
}