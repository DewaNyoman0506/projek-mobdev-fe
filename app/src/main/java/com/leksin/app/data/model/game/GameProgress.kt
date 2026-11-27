package com.leksin.app.data.model.game

data class GameProgress(
    val childId: String,
    val highestLevel: Int,
    val totalStars: Int,
    val currentStreak: Int,
    val longestStreak: Int,
    val lastActivityDate: String?,
)
