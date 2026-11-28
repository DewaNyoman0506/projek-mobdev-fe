package com.leksin.app.data.repository

import com.leksin.app.data.model.game.GameType
import com.leksin.app.data.remote.network.NetworkResult
import com.leksin.app.data.remote.datasource.GameRemoteDataSource
import com.leksin.app.data.remote.dto.request.GameSessionRequest
import com.leksin.app.data.remote.dto.response.GameHistoryResponse
import com.leksin.app.data.remote.dto.response.GameModeProgressResponse
import com.leksin.app.data.remote.dto.response.GameProgressResponse
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val remoteDataSource: GameRemoteDataSource,
) {

    suspend fun recordSession(
        childId: String,
        request: GameSessionRequest,
    ): NetworkResult<GameHistoryResponse> {
        return remoteDataSource.recordSession(childId, request)
    }

    suspend fun getHistory(
        childId: String,
        gameType: GameType? = null,
    ): NetworkResult<List<GameHistoryResponse>> {
        return remoteDataSource.getHistory(childId, gameType)
    }

    suspend fun getProgress(
        childId: String,
    ): NetworkResult<GameProgressResponse> {
        return remoteDataSource.getProgress(childId)
    }

    suspend fun getModeProgress(
        childId: String,
        gameType: GameType,
    ): NetworkResult<GameModeProgressResponse> {
        return remoteDataSource.getModeProgress(childId, gameType)
    }
}
