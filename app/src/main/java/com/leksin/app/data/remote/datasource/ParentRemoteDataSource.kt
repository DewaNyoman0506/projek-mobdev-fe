package com.leksin.app.data.remote.datasource

import com.leksin.app.data.remote.api.ParentApi
import com.leksin.app.data.remote.dto.request.ParentLoginRequest
import com.leksin.app.data.remote.dto.request.ParentRegisterRequest
import com.leksin.app.data.remote.dto.request.ParentUpdateRequest
import com.leksin.app.data.remote.dto.response.AuthResponse
import com.leksin.app.data.remote.dto.response.ParentResponse
import com.leksin.app.data.remote.network.NetworkCallHandler
import com.leksin.app.data.remote.network.NetworkResult
import javax.inject.Inject

class ParentRemoteDataSource @Inject constructor(
    private val parentApi: ParentApi,
    private val networkCallHandler: NetworkCallHandler,
) {

    suspend fun register(
        request: ParentRegisterRequest,
    ): NetworkResult<ParentResponse> {
        return networkCallHandler.safeApiCall {
            parentApi.register(request)
        }
    }

    suspend fun login(
        request: ParentLoginRequest,
    ): NetworkResult<AuthResponse> {
        return networkCallHandler.safeApiCall {
            parentApi.login(request)
        }
    }

    suspend fun getProfile(
        username: String,
    ): NetworkResult<ParentResponse> {
        return networkCallHandler.safeApiCall {
            parentApi.getProfile(username)
        }
    }

    suspend fun updateProfile(
        request: ParentUpdateRequest,
    ): NetworkResult<ParentResponse> {
        return networkCallHandler.safeApiCall {
            parentApi.updateProfile(request)
        }
    }
}
