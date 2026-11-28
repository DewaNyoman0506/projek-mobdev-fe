package com.leksin.app.data.repository

import com.leksin.app.data.mapper.toAppResult
import com.leksin.app.data.mapper.toDomain
import com.leksin.app.data.remote.datasource.GameRemoteDataSource
import com.leksin.app.data.remote.dto.request.GameSessionRequest
import com.leksin.app.domain.common.AppResult
import com.leksin.app.domain.model.game.GameHistory
import com.leksin.app.domain.model.game.GameModeProgress
import com.leksin.app.domain.model.game.GameProgress
import com.leksin.app.domain.model.game.GameType
import com.leksin.app.domain.repository.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val remoteDataSource: GameRemoteDataSource,
) : GameRepository {

    override suspend fun recordSession(
        childId: String,
        gameType: GameType,
        level: Int,
        durationSeconds: Int,
        attempts: Int,
        starsEarned: Int,
        completed: Boolean,
    ): AppResult<GameHistory> {
        val request = GameSessionRequest(
            gameType = gameType,
            level = level,
            durationSeconds = durationSeconds,
            attempts = attempts,
            starsEarned = starsEarned,
            completed = completed,
        )

        return remoteDataSource.recordSession(
            childId = childId,
            request = request,
        ).toAppResult { it.toDomain() }
    }

    override suspend fun getHistory(
        childId: String,
        gameType: GameType?,
    ): AppResult<List<GameHistory>> {
        return remoteDataSource.getHistory(
            childId = childId,
            gameType = gameType,
        ).toAppResult { responses ->
            responses.map { it.toDomain() }
        }
    }

    override suspend fun getProgress(
        childId: String,
    ): AppResult<GameProgress> {
        return remoteDataSource.getProgress(childId)
            .toAppResult { it.toDomain() }
    }

    override suspend fun getModeProgress(
        childId: String,
        gameType: GameType,
    ): AppResult<GameModeProgress> {
        return remoteDataSource.getModeProgress(
            childId = childId,
            gameType = gameType,
        ).toAppResult { it.toDomain() }
    }
}
