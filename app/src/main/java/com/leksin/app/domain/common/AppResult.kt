package com.leksin.app.domain.common

sealed interface AppResult<out T> {
    data class Success<T>(
        val data: T,
    ) : AppResult<T>

    data class Error(
        val statusCode: Int,
        val message: String,
        val fieldErrors: Map<String, String> = emptyMap(),
    ) : AppResult<Nothing>

    data class Exception(
        val message: String,
    ) : AppResult<Nothing>
}
