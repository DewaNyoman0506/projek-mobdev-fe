package com.leksin.app.data.remote.dto.response

import com.google.gson.annotations.SerializedName
import com.leksin.app.domain.model.game.GameType

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
