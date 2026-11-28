package com.leksin.app.data.repository

import com.leksin.app.data.local.TokenManager
import com.leksin.app.data.remote.network.NetworkResult
import com.leksin.app.data.remote.datasource.AuthRemoteDataSource
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val tokenManager: TokenManager,
) {

    suspend fun logout(): NetworkResult<Unit> {
        val result = remoteDataSource.logout()

        // Sesi lokal tetap diakhiri meskipun request logout gagal.
        tokenManager.clearSession()

        return result
    }

    fun isLoggedIn(): Boolean {
        return tokenManager.isLoggedIn()
    }
}