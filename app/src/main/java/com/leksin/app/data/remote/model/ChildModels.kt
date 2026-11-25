package com.leksin.app.data.remote.model

import com.google.gson.annotations.SerializedName

data class ChildCreateRequest(
    @SerializedName("nickname") val nickname: String
)

data class ChildResponse(
    @SerializedName("id") val id: String,
    @SerializedName("nickname") val nickname: String
)

data class ChildOtpRequest(
    @SerializedName("parentUsername") val parentUsername: String,
    @SerializedName("nickname") val nickname: String
)

data class ChildOtpVerifyRequest(
    @SerializedName("parentUsername") val parentUsername: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("code") val code: String
)

data class MessageResponse(
    @SerializedName("message") val message: String
)
