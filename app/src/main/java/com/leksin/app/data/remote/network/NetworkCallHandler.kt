package com.leksin.app.data.remote.network

import com.google.gson.Gson
import com.leksin.app.data.model.ProblemDetail
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class NetworkCallHandler @Inject constructor(
    private val gson: Gson,
) {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T,
    ): NetworkResult<T> {
        return try {
            NetworkResult.Success(apiCall())
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: HttpException) {
            NetworkResult.Error(
                statusCode = exception.code(),
                problemDetail = parseProblemDetail(exception),
            )
        } catch (exception: Exception) {
            NetworkResult.Exception(exception)
        }
    }

    private fun parseProblemDetail(
        exception: HttpException,
    ): ProblemDetail? {
        return try {
            val body = exception.response()?.errorBody()?.string()

            if (body.isNullOrBlank()) {
                null
            } else {
                gson.fromJson(body, ProblemDetail::class.java)
            }
        } catch (exception: Exception) {
            null
        }
    }
}