package com.leksin.app.data.repository

import com.leksin.app.data.local.TokenManager
import com.leksin.app.data.mapper.toAppResult
import com.leksin.app.data.mapper.toDomain
import com.leksin.app.data.remote.datasource.ChildAuthRemoteDataSource
import com.leksin.app.data.remote.dto.request.ChildOtpRequest
import com.leksin.app.data.remote.dto.request.ChildOtpVerifyRequest
import com.leksin.app.domain.common.AppResult
import com.leksin.app.domain.model.AuthenticatedUser
import com.leksin.app.domain.model.OtpResult
import com.leksin.app.domain.repository.ChildAuthRepository
import javax.inject.Inject

class ChildAuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: ChildAuthRemoteDataSource,
    private val tokenManager: TokenManager,
) : ChildAuthRepository {

    override suspend fun requestOtp(
        parentUsername: String,
        nickname: String,
    ): AppResult<OtpResult> {
        return remoteDataSource.requestOtp(
            ChildOtpRequest(
                parentUsername = parentUsername,
                nickname = nickname,
            )
        ).toAppResult {
            OtpResult(message = it.message)
        }
    }

    override suspend fun verifyOtp(
        parentUsername: String,
        nickname: String,
        code: String,
    ): AppResult<AuthenticatedUser> {
        val result = remoteDataSource.verifyOtp(
            ChildOtpVerifyRequest(
                parentUsername = parentUsername,
                nickname = nickname,
                code = code,
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
}
