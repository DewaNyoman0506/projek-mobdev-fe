package com.leksin.app.data.remote.dto.response

import com.google.gson.annotations.SerializedName
import com.leksin.app.data.model.game.GameHistory
import com.leksin.app.data.model.game.GameModeProgress
import com.leksin.app.data.model.game.GameProgress
import com.leksin.app.data.model.game.GameType

data class GameHistoryResponse(
    @SerializedName("id") val id: String,
    @SerializedName("gameType") val gameType: GameType,
    @SerializedName("level") val level: Int,
    @SerializedName("durationSeconds") val durationSeconds: Int,
    @SerializedName("attempts") val attempts: Int,
    @SerializedName("starsEarned") val starsEarned: Int,
    @SerializedName("completed") val completed: Boolean,
    @SerializedName("playedAt") val playedAt: String,
)

data class GameProgressResponse(
    @SerializedName("childId") val childId: String,
    @SerializedName("highestLevel") val highestLevel: Int,
    @SerializedName("totalStars") val totalStars: Int,
    @SerializedName("currentStreak") val currentStreak: Int,
    @SerializedName("longestStreak") val longestStreak: Int,
    @SerializedName("lastActivityDate") val lastActivityDate: String?,
)

data class GameModeProgressResponse(
    @SerializedName("childId") val childId: String,
    @SerializedName("gameType") val gameType: GameType,
    @SerializedName("highestLevel") val highestLevel: Int,
    @SerializedName("totalStars") val totalStars: Int,
)

// Extension Functions to Convert to Domain Model
fun GameHistoryResponse.toModel(): GameHistory {
    return GameHistory(
        id = this.id,
        gameType = this.gameType,
        level = this.level,
        durationSeconds = this.durationSeconds,
        attempts = this.attempts,
        starsEarned = this.starsEarned,
        completed = this.completed,
        playedAt = this.playedAt,
    )
}

fun GameProgressResponse.toModel(): GameProgress {
    return GameProgress(
        childId = this.childId,
        highestLevel = this.highestLevel,
        totalStars = this.totalStars,
        currentStreak = this.currentStreak,
        longestStreak = this.longestStreak,
        lastActivityDate = this.lastActivityDate,
    )
}

fun GameModeProgressResponse.toModel(): GameModeProgress {
    return GameModeProgress(
        childId = this.childId,
        gameType = this.gameType,
        highestLevel = this.highestLevel,
        totalStars = this.totalStars,
    )
}
