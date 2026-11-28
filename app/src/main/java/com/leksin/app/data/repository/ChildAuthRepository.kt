package com.leksin.app.data.repository

import com.leksin.app.data.local.TokenManager
import com.leksin.app.data.remote.datasource.ChildAuthRemoteDataSource
import com.leksin.app.data.remote.dto.request.ChildOtpRequest
import com.leksin.app.data.remote.dto.request.ChildOtpVerifyRequest
import com.leksin.app.data.remote.dto.response.AuthResponse
import com.leksin.app.data.remote.dto.response.OtpMessageResponse
import com.leksin.app.data.remote.network.NetworkResult
import javax.inject.Inject

class ChildAuthRepository @Inject constructor(
    private val remoteDataSource: ChildAuthRemoteDataSource,
    private val tokenManager: TokenManager,
) {

    suspend fun requestOtp(
        parentUsername: String,
        nickname: String,
    ): NetworkResult<OtpMessageResponse> {
        return remoteDataSource.requestOtp(
            ChildOtpRequest(parentUsername, nickname)
        )
    }

    suspend fun verifyOtp(
        parentUsername: String,
        nickname: String,
        code: String,
    ): NetworkResult<AuthResponse> {
        val result = remoteDataSource.verifyOtp(
            ChildOtpVerifyRequest(
                parentUsername = parentUsername,
                nickname = nickname,
                code = code,
            )
        )

        if (result is NetworkResult.Success) {
            saveSession(result.data)
        }

        return result
    }

    private fun saveSession(auth: AuthResponse) {
        tokenManager.saveAuthSession(
            token = auth.accessToken,
            userId = auth.userId,
            username = auth.username,
            role = auth.role,
            expiresInSeconds = auth.expiresIn,
        )
    }
}