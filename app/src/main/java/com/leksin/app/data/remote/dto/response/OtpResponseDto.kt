package com.leksin.app.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class OtpMessageResponse(
    @SerializedName("message") val message: String
)
