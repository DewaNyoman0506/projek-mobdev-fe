package com.leksin.app.data.remote.datasource

import com.leksin.app.domain.model.game.GameType
import com.leksin.app.data.remote.api.GameApi
import com.leksin.app.data.remote.dto.request.GameSessionRequest
import com.leksin.app.data.remote.dto.response.GameHistoryResponse
import com.leksin.app.data.remote.dto.response.GameModeProgressResponse
import com.leksin.app.data.remote.dto.response.GameProgressResponse
import com.leksin.app.data.remote.network.NetworkCallHandler
import com.leksin.app.data.remote.network.NetworkResult
import javax.inject.Inject

class GameRemoteDataSource @Inject constructor(
    private val gameApi: GameApi,
    private val networkCallHandler: NetworkCallHandler,
) {

    suspend fun recordSession(
        childId: String,
        request: GameSessionRequest,
    ): NetworkResult<GameHistoryResponse> {
        return networkCallHandler.safeApiCall {
            gameApi.recordGameSession(childId, request)
        }
    }

    suspend fun getHistory(
        childId: String,
        gameType: GameType? = null,
    ): NetworkResult<List<GameHistoryResponse>> {
        return networkCallHandler.safeApiCall {
            gameApi.getGameHistory(childId, gameType)
        }
    }

    suspend fun getProgress(
        childId: String,
    ): NetworkResult<GameProgressResponse> {
        return networkCallHandler.safeApiCall {
            gameApi.getGameProgress(childId)
        }
    }

    suspend fun getModeProgress(
        childId: String,
        gameType: GameType,
    ): NetworkResult<GameModeProgressResponse> {
        return networkCallHandler.safeApiCall {
            gameApi.getGameModeProgress(childId, gameType)
        }
    }
}