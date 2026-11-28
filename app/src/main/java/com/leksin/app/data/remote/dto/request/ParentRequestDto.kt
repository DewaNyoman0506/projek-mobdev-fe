package com.leksin.app.data.remote.dto.request

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

data class ParentUpdateRequest(
    @SerializedName("username") val username: String? = null,
    @SerializedName("email") val email: String? = null,
)

data class ParentOtpRequest(
    @SerializedName("username") val username: String,
)

data class ParentOtpVerifyRequest(
    @SerializedName("username") val username: String,
    @SerializedName("code") val code: String,
)
