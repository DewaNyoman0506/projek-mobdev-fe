package com.leksin.app.data.remote

import com.leksin.app.data.model.ProblemDetail

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(
        val statusCode: Int,
        val problemDetail: ProblemDetail? = null,
        val message: String = problemDetail?.detail ?: problemDetail?.title ?: "HTTP Error $statusCode"
    ) : NetworkResult<Nothing>()
    data class Exception(val throwable: Throwable) : NetworkResult<Nothing>()
}

fun <T, R> NetworkResult<T>.map(transform: (T) -> R): NetworkResult<R> = when (this) {
    is NetworkResult.Success -> NetworkResult.Success(transform(data))
    is NetworkResult.Error -> this
    is NetworkResult.Exception -> this
}
