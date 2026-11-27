package com.leksin.app.data.model.game

data class GameModeProgress(
    val childId: String,
    val gameType: GameType,
    val highestLevel: Int,
    val totalStars: Int,
)
