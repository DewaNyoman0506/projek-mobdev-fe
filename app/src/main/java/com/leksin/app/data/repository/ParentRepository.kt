package com.leksin.app.data.repository

import com.leksin.app.data.local.TokenManager
import com.leksin.app.data.remote.datasource.ParentOtpRemoteDataSource
import com.leksin.app.data.remote.datasource.ParentRemoteDataSource
import com.leksin.app.data.remote.dto.request.ParentLoginRequest
import com.leksin.app.data.remote.dto.request.ParentOtpRequest
import com.leksin.app.data.remote.dto.request.ParentOtpVerifyRequest
import com.leksin.app.data.remote.dto.request.ParentRegisterRequest
import com.leksin.app.data.remote.dto.request.ParentUpdateRequest
import com.leksin.app.data.remote.dto.response.AuthResponse
import com.leksin.app.data.remote.dto.response.OtpMessageResponse
import com.leksin.app.data.remote.dto.response.ParentResponse
import com.leksin.app.data.remote.network.NetworkResult
import javax.inject.Inject

class ParentRepository @Inject constructor(
    private val parentRemoteDataSource: ParentRemoteDataSource,
    private val parentOtpRemoteDataSource: ParentOtpRemoteDataSource,
    private val tokenManager: TokenManager,
) {

    suspend fun register(
        request: ParentRegisterRequest,
    ): NetworkResult<ParentResponse> {
        return parentRemoteDataSource.register(request)
    }

    suspend fun login(
        request: ParentLoginRequest,
    ): NetworkResult<AuthResponse> {
        val result = parentRemoteDataSource.login(request)

        if (result is NetworkResult.Success) {
            saveSession(result.data)
        }

        return result
    }

    suspend fun requestOtp(
        username: String,
    ): NetworkResult<OtpMessageResponse> {
        return parentOtpRemoteDataSource.requestOtp(
            ParentOtpRequest(username)
        )
    }

    suspend fun verifyOtp(
        username: String,
        code: String,
    ): NetworkResult<OtpMessageResponse> {
        return parentOtpRemoteDataSource.verifyOtp(
            ParentOtpVerifyRequest(username, code)
        )
    }

    suspend fun getProfile(
        username: String,
    ): NetworkResult<ParentResponse> {
        return parentRemoteDataSource.getProfile(username)
    }

    suspend fun updateProfile(
        request: ParentUpdateRequest,
    ): NetworkResult<ParentResponse> {
        return parentRemoteDataSource.updateProfile(request)
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