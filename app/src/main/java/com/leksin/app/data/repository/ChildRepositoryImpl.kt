package com.leksin.app.data.repository

import com.leksin.app.data.mapper.toAppResult
import com.leksin.app.data.mapper.toDomain
import com.leksin.app.data.remote.datasource.ChildRemoteDataSource
import com.leksin.app.data.remote.dto.request.ChildCreateRequest
import com.leksin.app.data.remote.dto.request.ChildUpdateRequest
import com.leksin.app.domain.common.AppResult
import com.leksin.app.domain.model.Child
import com.leksin.app.domain.repository.ChildRepository
import javax.inject.Inject

class ChildRepositoryImpl @Inject constructor(
    private val remoteDataSource: ChildRemoteDataSource,
) : ChildRepository {

    override suspend fun createChild(
        nickname: String,
    ): AppResult<Child> {
        return remoteDataSource.createChild(
            ChildCreateRequest(nickname)
        ).toAppResult { it.toDomain() }
    }

    override suspend fun getChildren(): AppResult<List<Child>> {
        return remoteDataSource.getChildren()
            .toAppResult { responses ->
                responses.map { it.toDomain() }
            }
    }

    override suspend fun updateChild(
        childId: String,
        nickname: String,
    ): AppResult<Child> {
        return remoteDataSource.updateChild(
            childId = childId,
            request = ChildUpdateRequest(nickname),
        ).toAppResult { it.toDomain() }
    }

    override suspend fun deleteChild(
        childId: String,
    ): AppResult<Unit> {
        return remoteDataSource.deleteChild(childId)
            .toAppResult()
    }
}
