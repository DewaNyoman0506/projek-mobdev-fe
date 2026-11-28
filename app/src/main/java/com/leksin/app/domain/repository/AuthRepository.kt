package com.leksin.app.domain.repository

import com.leksin.app.domain.common.AppResult

interface AuthRepository {
    suspend fun logout(): AppResult<Unit>

    fun isLoggedIn(): Boolean
}
