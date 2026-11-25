package com.leksin.app.data.remote

import android.content.Context
import com.google.gson.Gson
import com.leksin.app.data.local.TokenManager
import com.leksin.app.data.remote.model.ProblemDetail
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    // Const Base URL
    const val BASE_URL_EMULATOR = "http://10.0.2.2:8080/"
    const val BASE_URL_PRODUCTION = "https://leksin-backend.onrender.com/" // Update ke domain kalo ada

    // Base URL Aktif
    var currentBaseUrl: String = BASE_URL_EMULATOR

    private var retrofit: Retrofit? = null
    private var apiService: LeksinApi? = null
    private val gson = Gson()

    /**
     * Helper u/ set custom LAN IP
     * Contoj: setCustomLanIp("192.168.1.50") -> http://192.168.1.50:8080/
     */
    fun setCustomLanIp(ipAddress: String, port: Int = 8080) {
        val cleanIp = ipAddress.trim().removePrefix("http://").removePrefix("https://").removeSuffix("/")
        currentBaseUrl = "http://$cleanIp:$port/"
        resetClient()
    }

    /**
     * Switch ke Base URL manual
     */
    fun setBaseUrl(url: String) {
        currentBaseUrl = if (url.endsWith("/")) url else "$url/"
        resetClient()
    }

    private fun resetClient() {
        retrofit = null
        apiService = null
    }

    fun getApiService(context: Context): LeksinApi {
        return apiService ?: synchronized(this) {
            apiService ?: buildRetrofit(context).create(LeksinApi::class.java).also {
                apiService = it
            }
        }
    }

    private fun buildRetrofit(context: Context): Retrofit {
        val tokenManager = TokenManager(context.applicationContext)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        val instance = Retrofit.Builder()
            .baseUrl(currentBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit = instance
        return instance
    }

    /**
     * Suspend API
     */
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
        return try {
            NetworkResult.Success(apiCall())
        } catch (e: HttpException) {
            val statusCode = e.code()
            val problemDetail = parseProblemDetail(e)
            NetworkResult.Error(
                statusCode = statusCode,
                problemDetail = problemDetail
            )
        } catch (e: Throwable) {
            NetworkResult.Exception(e)
        }
    }

    private fun parseProblemDetail(httpException: HttpException): ProblemDetail? {
        return try {
            val errorBodyString = httpException.response()?.errorBody()?.string()
            if (!errorBodyString.isNull_OrBlank()) {
                gson.fromJson(errorBodyString, ProblemDetail::class.java)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun String?.isNull_OrBlank(): Boolean = this == null || this.trim().isEmpty()
}
