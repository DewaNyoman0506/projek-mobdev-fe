package com.leksin.app.data.remote.dto.request

import com.google.gson.annotations.SerializedName
import com.leksin.app.domain.model.game.GameType

data class GameSessionRequest(
    @SerializedName("gameType") val gameType: GameType,
    @SerializedName("level") val level: Int,
    @SerializedName("durationSeconds") val durationSeconds: Int,
    @SerializedName("attempts") val attempts: Int,
    @SerializedName("starsEarned") val starsEarned: Int,
    @SerializedName("completed") val completed: Boolean
)
