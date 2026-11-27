package com.leksin.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Standard Spring Boot RFC 7807 ProblemDetail error format.
 */
data class ProblemDetail(
    @SerializedName("type") val type: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("status") val status: Int? = null,
    @SerializedName("detail") val detail: String? = null,
    @SerializedName("timestamp") val timestamp: String? = null,
    @SerializedName("errors") val errors: Map<String, String>? = null
)
