package com.leksin.app.data.remote.dto.response

import com.google.gson.annotations.SerializedName
import com.leksin.app.data.model.OtpMessage

data class OtpMessageResponse(
    @SerializedName("message") val message: String
)

// Extension Functions to Convert to Domain Model
fun OtpMessageResponse.toModel(): OtpMessage {
    return OtpMessage(this.message)
}