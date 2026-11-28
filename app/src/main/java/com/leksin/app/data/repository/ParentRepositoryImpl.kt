package com.leksin.app.data.repository

import com.leksin.app.data.local.TokenManager
import com.leksin.app.data.mapper.toAppResult
import com.leksin.app.data.mapper.toDomain
import com.leksin.app.data.remote.datasource.ParentOtpRemoteDataSource
import com.leksin.app.data.remote.datasource.ParentRemoteDataSource
import com.leksin.app.data.remote.dto.request.ParentLoginRequest
import com.leksin.app.data.remote.dto.request.ParentOtpRequest
import com.leksin.app.data.remote.dto.request.ParentOtpVerifyRequest
import com.leksin.app.data.remote.dto.request.ParentRegisterRequest
import com.leksin.app.data.remote.dto.request.ParentUpdateRequest
import com.leksin.app.domain.common.AppResult
import com.leksin.app.domain.model.AuthenticatedUser
import com.leksin.app.domain.model.OtpResult
import com.leksin.app.domain.model.Parent
import com.leksin.app.domain.repository.ParentRepository
import javax.inject.Inject

class ParentRepositoryImpl @Inject constructor(
    private val remoteDataSource: ParentRemoteDataSource,
    private val otpRemoteDataSource: ParentOtpRemoteDataSource,
    private val tokenManager: TokenManager,
) : ParentRepository {

    override suspend fun register(
        username: String,
        password: String,
        email: String,
    ): AppResult<Parent> {
        return remoteDataSource.register(
            ParentRegisterRequest(
                username = username,
                password = password,
                email = email,
            )
        ).toAppResult { it.toDomain() }
    }

    override suspend fun login(
        username: String,
        password: String,
    ): AppResult<AuthenticatedUser> {
        val result = remoteDataSource.login(
            ParentLoginRequest(
                username = username,
                password = password,
            )
        )

        if (result is com.leksin.app.data.remote.network.NetworkResult.Success) {
            val auth = result.data

            tokenManager.saveAuthSession(
                token = auth.accessToken,
                userId = auth.userId,
                username = auth.username,
                role = auth.role,
                expiresInSeconds = auth.expiresIn,
            )
        }

        return result.toAppResult { it.toDomain() }
    }

    override suspend fun requestOtp(
        username: String,
    ): AppResult<OtpResult> {
        return otpRemoteDataSource.requestOtp(
            ParentOtpRequest(username)
        ).toAppResult {
            OtpResult(message = it.message)
        }
    }

    override suspend fun verifyOtp(
        username: String,
        code: String,
    ): AppResult<OtpResult> {
        return otpRemoteDataSource.verifyOtp(
            ParentOtpVerifyRequest(username, code)
        ).toAppResult {
            OtpResult(message = it.message)
        }
    }

    override suspend fun getProfile(
        username: String,
    ): AppResult<Parent> {
        return remoteDataSource.getProfile(username)
            .toAppResult { it.toDomain() }
    }

    override suspend fun updateProfile(
        username: String?,
        email: String?,
    ): AppResult<Parent> {
        return remoteDataSource.updateProfile(
            ParentUpdateRequest(
                username = username,
                email = email,
            )
        ).toAppResult { it.toDomain() }
    }
}
