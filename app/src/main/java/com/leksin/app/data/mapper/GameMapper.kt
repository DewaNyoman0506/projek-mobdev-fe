package com.leksin.app.data.mapper

import com.leksin.app.data.remote.dto.response.GameHistoryResponse
import com.leksin.app.data.remote.dto.response.GameModeProgressResponse
import com.leksin.app.data.remote.dto.response.GameProgressResponse
import com.leksin.app.domain.model.game.GameHistory
import com.leksin.app.domain.model.game.GameModeProgress
import com.leksin.app.domain.model.game.GameProgress

fun GameHistoryResponse.toDomain(): GameHistory {
    return GameHistory(
        id = id,
        gameType = gameType,
        level = level,
        durationSeconds = durationSeconds,
        attempts = attempts,
        starsEarned = starsEarned,
        completed = completed,
        playedAt = playedAt,
    )
}

fun GameProgressResponse.toDomain(): GameProgress {
    return GameProgress(
        childId = childId,
        highestLevel = highestLevel,
        totalStars = totalStars,
        currentStreak = currentStreak,
        longestStreak = longestStreak,
        lastActivityDate = lastActivityDate,
    )
}

fun GameModeProgressResponse.toDomain(): GameModeProgress {
    return GameModeProgress(
        childId = childId,
        gameType = gameType,
        highestLevel = highestLevel,
        totalStars = totalStars,
    )
}
