package com.leksin.app.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("userId") val userId: String,
    @SerializedName("username") val username: String,
    @SerializedName("role") val role: String,
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("tokenType") val tokenType: String,
    @SerializedName("expiresIn") val expiresIn: Long
)
