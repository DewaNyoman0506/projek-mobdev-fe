package com.leksin.app.data.remote.dto.response

import com.google.gson.annotations.SerializedName
import com.leksin.app.domain.model.Child

data class ChildResponse(
    @SerializedName("id") val id: String,
    @SerializedName("nickname") val nickname: String
)