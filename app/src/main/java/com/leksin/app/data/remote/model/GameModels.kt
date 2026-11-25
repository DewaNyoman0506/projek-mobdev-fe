package com.leksin.app.data.remote.model

import com.google.gson.annotations.SerializedName

data class GameSessionRequest(
    @SerializedName("level") val level: Int,
    @SerializedName("durationSeconds") val durationSeconds: Int,
    @SerializedName("attempts") val attempts: Int,
    @SerializedName("starsEarned") val starsEarned: Int,
    @SerializedName("completed") val completed: Boolean
)

data class GameHistoryResponse(
    @SerializedName("id") val id: String,
    @SerializedName("level") val level: Int,
    @SerializedName("durationSeconds") val durationSeconds: Int,
    @SerializedName("attempts") val attempts: Int,
    @SerializedName("starsEarned") val starsEarned: Int,
    @SerializedName("completed") val completed: Boolean,
    @SerializedName("playedAt") val playedAt: String
)

data class GameProgressResponse(
    @SerializedName("childId") val childId: String,
    @SerializedName("highestLevel") val highestLevel: Int,
    @SerializedName("totalStars") val totalStars: Int,
    @SerializedName("currentStreak") val currentStreak: Int,
    @SerializedName("longestStreak") val longestStreak: Int,
    @SerializedName("lastActivityDate") val lastActivityDate: String?
)
