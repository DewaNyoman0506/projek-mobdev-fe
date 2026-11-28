package com.leksin.app.domain.model

data class AuthenticatedUser(
    val userId: String,
    val username: String,
    val role: String,
)
