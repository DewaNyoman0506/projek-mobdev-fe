package com.leksin.app.domain.model.game

data class GameModeProgress(
    val childId: String,
    val gameType: GameType,
    val highestLevel: Int,
    val totalStars: Int,
)
