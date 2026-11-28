package com.leksin.app.domain.repository

import com.leksin.app.domain.common.AppResult
import com.leksin.app.domain.model.Child

interface ChildRepository {
    suspend fun createChild(
        nickname: String,
    ): AppResult<Child>

    suspend fun getChildren(): AppResult<List<Child>>

    suspend fun updateChild(
        childId: String,
        nickname: String,
    ): AppResult<Child>

    suspend fun deleteChild(
        childId: String,
    ): AppResult<Unit>
}
