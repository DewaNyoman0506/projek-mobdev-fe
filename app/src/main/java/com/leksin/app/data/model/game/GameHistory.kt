package com.leksin.app.data.model.game

data class GameHistory(
    val id: String,
    val gameType: GameType,
    val level: Int,
    val durationSeconds: Int,
    val attempts: Int,
    val starsEarned: Int,
    val completed: Boolean,
    val playedAt: String,
)
