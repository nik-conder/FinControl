package com.app.myfincontrol.data.sources.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.myfincontrol.data.entities.Session
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSession(session: Session): Long

    @Query("DELETE FROM session")
    suspend fun deleteAllSessions()

    @Query("SELECT * FROM session")
    fun getAllSession(): Flow<List<Session>>
}