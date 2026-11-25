package com.leksin.app.data.remote.model

import com.google.gson.annotations.SerializedName

data class ParentRegisterRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("email") val email: String
)

data class ParentLoginRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

data class ParentResponse(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String
)

data class AuthResponse(
    @SerializedName("userId") val userId: String,
    @SerializedName("username") val username: String,
    @SerializedName("role") val role: String,
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("tokenType") val tokenType: String,
    @SerializedName("expiresIn") val expiresIn: Long
)
