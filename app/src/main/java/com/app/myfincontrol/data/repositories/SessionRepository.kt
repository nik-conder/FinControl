package com.app.myfincontrol.data.repositories

import com.app.myfincontrol.data.entities.Session
import com.app.myfincontrol.data.sources.database.SessionDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SessionRepository @Inject constructor(
    private val sessionDAO: SessionDAO
) {

    suspend fun setSession(session: Session): Long {
        sessionDAO.deleteAllSessions()
        return sessionDAO.insertSession(session)
    }

    fun getAllSession(): Flow<List<Session>> {
        return sessionDAO.getAllSession()
    }

    fun getLastSession(): Session {
        return sessionDAO.getLastSession()
    }

}