package com.app.myfincontrol.domain.useCases

import com.app.myfincontrol.data.entities.Session
import com.app.myfincontrol.data.repositories.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) {

    suspend fun setSession(session: Session) = sessionRepository.setSession(session)

    fun getAllSessions(): Flow<List<Session>> = sessionRepository.getAllSessions()
    fun getLastSession(): Session = sessionRepository.getLastSession()

    suspend fun deleteSession() = sessionRepository.deleteAllSessions()
}