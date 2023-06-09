package com.app.myfincontrol.domain.useCases

import com.app.myfincontrol.data.entities.Session
import com.app.myfincontrol.data.repositories.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
) {

    fun getSession() = sessionRepository.getSession()

    suspend fun setSession(session: Session) = sessionRepository.setSession(session)

    suspend fun getAllSession(): Flow<List<Session>> = sessionRepository.getAllSession()
}