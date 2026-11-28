package com.leksin.app.data.remote.datasource

import com.leksin.app.data.remote.api.AuthApi
import com.leksin.app.data.remote.network.NetworkCallHandler
import com.leksin.app.data.remote.network.NetworkResult
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authApi: AuthApi,
    private val networkCallHandler: NetworkCallHandler,
) {

    suspend fun logout(): NetworkResult<Unit> {
        return networkCallHandler.safeApiCall {
            authApi.logout()
        }
    }
}