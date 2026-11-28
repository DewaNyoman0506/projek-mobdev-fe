package com.leksin.app.data.mapper

import com.leksin.app.data.remote.dto.response.AuthResponse
import com.leksin.app.domain.model.AuthenticatedUser

fun AuthResponse.toDomain(): AuthenticatedUser {
    return AuthenticatedUser(
        userId = userId,
        username = username,
        role = role,
    )
}
