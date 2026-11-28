package com.leksin.app.domain.repository

import com.leksin.app.domain.common.AppResult
import com.leksin.app.domain.model.AuthenticatedUser
import com.leksin.app.domain.model.OtpResult

interface ChildAuthRepository {
    suspend fun requestOtp(
        parentUsername: String,
        nickname: String,
    ): AppResult<OtpResult>

    suspend fun verifyOtp(
        parentUsername: String,
        nickname: String,
        code: String,
    ): AppResult<AuthenticatedUser>
}
