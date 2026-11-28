package com.leksin.app.data.remote.api

import com.leksin.app.data.remote.dto.request.ChildOtpRequest
import com.leksin.app.data.remote.dto.request.ChildOtpVerifyRequest
import com.leksin.app.data.remote.dto.response.AuthResponse
import com.leksin.app.data.remote.dto.response.OtpResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ChildOtpApi {
    @POST("child/auth/request-otp")
    suspend fun requestOtp(
        @Body request: ChildOtpRequest,
    ): OtpResponseDto

    @POST("child/auth/verify-otp")
    suspend fun verifyOtp(
        @Body request: ChildOtpVerifyRequest,
    ): AuthResponse
}