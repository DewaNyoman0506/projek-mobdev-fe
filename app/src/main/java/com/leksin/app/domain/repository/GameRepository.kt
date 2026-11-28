package com.leksin.app.domain.repository

import com.leksin.app.domain.common.AppResult
import com.leksin.app.domain.model.game.GameHistory
import com.leksin.app.domain.model.game.GameModeProgress
import com.leksin.app.domain.model.game.GameProgress
import com.leksin.app.domain.model.game.GameType

interface GameRepository {
    suspend fun recordSession(
        childId: String,
        gameType: GameType,
        level: Int,
        durationSeconds: Int,
        attempts: Int,
        starsEarned: Int,
        completed: Boolean,
    ): AppResult<GameHistory>

    suspend fun getHistory(
        childId: String,
        gameType: GameType? = null,
    ): AppResult<List<GameHistory>>

    suspend fun getProgress(
        childId: String,
    ): AppResult<GameProgress>

    suspend fun getModeProgress(
        childId: String,
        gameType: GameType,
    ): AppResult<GameModeProgress>
}
