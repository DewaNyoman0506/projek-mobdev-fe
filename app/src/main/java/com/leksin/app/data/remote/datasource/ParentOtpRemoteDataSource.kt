package com.leksin.app.data.remote.datasource

import com.leksin.app.data.remote.api.ParentOtpApi
import com.leksin.app.data.remote.dto.request.ParentOtpRequest
import com.leksin.app.data.remote.dto.request.ParentOtpVerifyRequest
import com.leksin.app.data.remote.dto.response.OtpResponseDto
import com.leksin.app.data.remote.network.NetworkCallHandler
import com.leksin.app.data.remote.network.NetworkResult
import javax.inject.Inject

class ParentOtpRemoteDataSource @Inject constructor(
    private val parentOtpApi: ParentOtpApi,
    private val networkCallHandler: NetworkCallHandler,
) {

    suspend fun requestOtp(
        request: ParentOtpRequest,
    ): NetworkResult<OtpResponseDto> {
        return networkCallHandler.safeApiCall {
            parentOtpApi.requestOtp(request)
        }
    }

    suspend fun verifyOtp(
        request: ParentOtpVerifyRequest,
    ): NetworkResult<OtpResponseDto> {
        return networkCallHandler.safeApiCall {
            parentOtpApi.verifyOtp(request)
        }
    }
}