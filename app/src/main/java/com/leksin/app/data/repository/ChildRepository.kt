package com.leksin.app.data.repository

import com.leksin.app.data.remote.network.NetworkResult
import com.leksin.app.data.remote.datasource.ChildRemoteDataSource
import com.leksin.app.data.remote.dto.request.ChildCreateRequest
import com.leksin.app.data.remote.dto.request.ChildUpdateRequest
import com.leksin.app.data.remote.dto.response.ChildResponse
import javax.inject.Inject

class ChildRepository @Inject constructor(
    private val remoteDataSource: ChildRemoteDataSource,
) {

    suspend fun createChild(
        nickname: String,
    ): NetworkResult<ChildResponse> {
        return remoteDataSource.createChild(
            ChildCreateRequest(nickname)
        )
    }

    suspend fun getChildren(): NetworkResult<List<ChildResponse>> {
        return remoteDataSource.getChildren()
    }

    suspend fun updateChild(
        childId: String,
        nickname: String,
    ): NetworkResult<ChildResponse> {
        return remoteDataSource.updateChild(
            childId,
            ChildUpdateRequest(nickname),
        )
    }

    suspend fun deleteChild(
        childId: String,
    ): NetworkResult<Unit> {
        return remoteDataSource.deleteChild(childId)
    }
}