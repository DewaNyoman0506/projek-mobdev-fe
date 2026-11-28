package com.leksin.app.data.mapper

import com.leksin.app.data.remote.network.NetworkResult
import com.leksin.app.domain.common.AppResult

fun <T, R> NetworkResult<T>.toAppResult(
    transform: (T) -> R,
): AppResult<R> {
    return when (this) {
        is NetworkResult.Success -> {
            AppResult.Success(transform(data))
        }

        is NetworkResult.Error -> {
            AppResult.Error(
                statusCode = statusCode,
                message = message,
                fieldErrors = problemDetail?.errors.orEmpty(),
            )
        }

        is NetworkResult.Exception -> {
            AppResult.Exception(
                message = throwable.message ?: "Terjadi kesalahan.",
            )
        }
    }
}

fun NetworkResult<Unit>.toAppResult(): AppResult<Unit> {
    return toAppResult { it }
}
