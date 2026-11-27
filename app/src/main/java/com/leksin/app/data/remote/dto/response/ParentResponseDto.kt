package com.leksin.app.data.remote.dto.response

import com.google.gson.annotations.SerializedName
import com.leksin.app.data.model.Parent

data class ParentResponse(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String
)

// Extension Functions to Convert to Domain Model
fun ParentResponse.toModel(): Parent {
    return Parent(
        id = this.id,
        username = this.username,
        email = this.email,
    )
}
