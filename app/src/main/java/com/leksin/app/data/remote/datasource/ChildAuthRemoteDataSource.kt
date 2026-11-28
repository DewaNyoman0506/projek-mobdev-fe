package com.leksin.app.data.remote.datasource

import com.leksin.app.data.remote.api.ChildOtpApi
import com.leksin.app.data.remote.dto.request.ChildOtpRequest
import com.leksin.app.data.remote.dto.request.ChildOtpVerifyRequest
import com.leksin.app.data.remote.dto.response.AuthResponse
import com.leksin.app.data.remote.dto.response.OtpResponseDto
import com.leksin.app.data.remote.network.NetworkCallHandler
import com.leksin.app.data.remote.network.NetworkResult
import javax.inject.Inject

class ChildAuthRemoteDataSource @Inject constructor(
    private val childOtpApi: ChildOtpApi,
    private val networkCallHandler: NetworkCallHandler,
) {

    suspend fun requestOtp(
        request: ChildOtpRequest,
    ): NetworkResult<OtpResponseDto> {
        return networkCallHandler.safeApiCall {
            childOtpApi.requestOtp(request)
        }
    }

    suspend fun verifyOtp(
        request: ChildOtpVerifyRequest,
    ): NetworkResult<AuthResponse> {
        return networkCallHandler.safeApiCall {
            childOtpApi.verifyOtp(request)
        }
    }
}