package com.leksin.app.data.remote.api

import com.leksin.app.data.remote.dto.request.ParentOtpRequest
import com.leksin.app.data.remote.dto.request.ParentOtpVerifyRequest
import com.leksin.app.data.remote.dto.response.OtpMessageResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ParentOtpApi {

    @POST("parent/auth/request-otp")
    suspend fun requestOtp(
        @Body request: ParentOtpRequest,
    ): OtpMessageResponse

    @POST("parent/auth/verify-otp")
    suspend fun verifyOtp(
        @Body request: ParentOtpVerifyRequest,
    ): OtpMessageResponse
}
