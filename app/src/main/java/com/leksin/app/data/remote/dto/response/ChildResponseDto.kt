package com.leksin.app.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class ChildResponse(
    @SerializedName("id") val id: String,
    @SerializedName("nickname") val nickname: String
)