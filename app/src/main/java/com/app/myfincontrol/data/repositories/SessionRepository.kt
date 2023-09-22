package com.app.myfincontrol.data.repositories

import com.app.myfincontrol.data.entities.Session
import com.app.myfincontrol.data.sources.database.SessionDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SessionRepository @Inject constructor(
    private val sessionDAO: SessionDAO
) {


    private suspend fun insertSession(session: Session): Long {
        return sessionDAO.insertSession(session)
    }

    suspend fun setSession(session: Session): Long {
        sessionDAO.deleteAllSessions()
        return insertSession(session)
    }

    fun getAllSessions(): Flow<List<Session>> {
        return sessionDAO.getAllSessions()
    }

    fun getLastSession(): Session {
        return sessionDAO.getLastSession()
    }

    suspend fun deleteAllSessions() {
        return sessionDAO.deleteAllSessions()
    }

}