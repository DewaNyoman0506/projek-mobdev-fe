package com.leksin.app.data.remote

import com.leksin.app.data.remote.model.ProblemDetail

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(
        val statusCode: Int,
        val problemDetail: ProblemDetail? = null,
        val message: String = problemDetail?.detail ?: problemDetail?.title ?: "HTTP Error $statusCode"
    ) : NetworkResult<Nothing>()
    data class Exception(val throwable: Throwable) : NetworkResult<Nothing>()
}
