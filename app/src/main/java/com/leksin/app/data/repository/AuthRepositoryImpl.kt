package com.leksin.app.data.repository

import com.leksin.app.data.local.TokenManager
import com.leksin.app.data.mapper.toAppResult
import com.leksin.app.data.remote.datasource.AuthRemoteDataSource
import com.leksin.app.domain.common.AppResult
import com.leksin.app.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val tokenManager: TokenManager,
) : AuthRepository {

    override suspend fun logout(): AppResult<Unit> {
        val result = remoteDataSource.logout()

        tokenManager.clearSession()

        return result.toAppResult()
    }

    override fun isLoggedIn(): Boolean {
        return tokenManager.isLoggedIn()
    }
}
