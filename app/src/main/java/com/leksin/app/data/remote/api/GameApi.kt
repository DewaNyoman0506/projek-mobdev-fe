package com.leksin.app.data.remote.api

import com.leksin.app.data.model.game.GameType
import com.leksin.app.data.remote.dto.request.GameSessionRequest
import com.leksin.app.data.remote.dto.response.GameHistoryResponse
import com.leksin.app.data.remote.dto.response.GameModeProgressResponse
import com.leksin.app.data.remote.dto.response.GameProgressResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GameApi {

    @POST("$GAMES_PATH/sessions")
    suspend fun recordGameSession(
        @Path("childId") childId: String,
        @Body request: GameSessionRequest,
    ): GameHistoryResponse

    @GET("$GAMES_PATH/history")
    suspend fun getGameHistory(
        @Path("childId") childId: String,
        @Query("gameType") gameType: GameType? = null,
    ): List<GameHistoryResponse>

    @GET("$GAMES_PATH/progress")
    suspend fun getGameProgress(
        @Path("childId") childId: String,
    ): GameProgressResponse

    @GET("$GAMES_PATH/progress/{gameType}")
    suspend fun getGameModeProgress(
        @Path("childId") childId: String,
        @Path("gameType") gameType: GameType,
    ): GameModeProgressResponse

    companion object {
        const val GAMES_PATH = "child/{childId}/games"
    }
}