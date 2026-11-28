package com.leksin.app.domain.repository

import com.leksin.app.domain.common.AppResult
import com.leksin.app.domain.model.AuthenticatedUser
import com.leksin.app.domain.model.OtpResult
import com.leksin.app.domain.model.Parent

interface ParentRepository {
    suspend fun register(
        username: String,
        password: String,
        email: String,
    ): AppResult<Parent>

    suspend fun login(
        username: String,
        password: String,
    ): AppResult<AuthenticatedUser>

    suspend fun requestOtp(
        username: String,
    ): AppResult<OtpResult>

    suspend fun verifyOtp(
        username: String,
        code: String,
    ): AppResult<OtpResult>

    suspend fun getProfile(
        username: String,
    ): AppResult<Parent>

    suspend fun updateProfile(
        username: String?,
        email: String?,
    ): AppResult<Parent>
}
