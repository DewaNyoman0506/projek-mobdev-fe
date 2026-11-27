package com.leksin.app.data.remote.dto.request

import com.google.gson.annotations.SerializedName

data class ChildCreateRequest(
    @SerializedName("nickname") val nickname: String,
)

data class ChildUpdateRequest(
    @SerializedName("nickname") val nickname: String,
)

data class ChildOtpRequest(
    @SerializedName("parentUsername") val parentUsername: String,
    @SerializedName("nickname") val nickname: String,
)

data class ChildOtpVerifyRequest(
    @SerializedName("parentUsername") val parentUsername: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("code") val code: String,
)
