package com.leksin.app.data.remote.dto.response

import com.google.gson.annotations.SerializedName
import com.leksin.app.data.model.Child
import com.leksin.app.data.model.OtpMessage

data class ChildResponse(
    @SerializedName("id") val id: String,
    @SerializedName("nickname") val nickname: String
)

data class OtpMessageResponse(
    @SerializedName("message") val message: String
)

// Extension Functions to Convert to Domain Model
fun ChildResponse.toModel(): Child {
    return Child(
        id = this.id,
        nickname = this.nickname,
    )
}

fun OtpMessageResponse.toModel(): OtpMessage {
    return OtpMessage(this.message)
}
