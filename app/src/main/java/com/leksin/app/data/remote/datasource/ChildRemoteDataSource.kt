package com.leksin.app.data.remote.datasource

import com.leksin.app.data.remote.api.ChildApi
import com.leksin.app.data.remote.dto.request.ChildCreateRequest
import com.leksin.app.data.remote.dto.request.ChildUpdateRequest
import com.leksin.app.data.remote.dto.response.ChildResponse
import com.leksin.app.data.remote.network.NetworkCallHandler
import com.leksin.app.data.remote.network.NetworkResult
import javax.inject.Inject

class ChildRemoteDataSource @Inject constructor(
    private val childApi: ChildApi,
    private val networkCallHandler: NetworkCallHandler,
) {

    suspend fun createChild(
        request: ChildCreateRequest,
    ): NetworkResult<ChildResponse> {
        return networkCallHandler.safeApiCall {
            childApi.createChild(request)
        }
    }

    suspend fun getChildren(): NetworkResult<List<ChildResponse>> {
        return networkCallHandler.safeApiCall {
            childApi.getChildren()
        }
    }

    suspend fun updateChild(
        childId: String,
        request: ChildUpdateRequest,
    ): NetworkResult<ChildResponse> {
        return networkCallHandler.safeApiCall {
            childApi.updateChildById(childId, request)
        }
    }

    suspend fun deleteChild(
        childId: String,
    ): NetworkResult<Unit> {
        return networkCallHandler.safeApiCall {
            childApi.deleteChildById(childId)
        }
    }
}