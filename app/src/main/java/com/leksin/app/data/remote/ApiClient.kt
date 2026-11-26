package com.leksin.app.data.remote

import android.content.Context
import com.google.gson.Gson
import com.leksin.app.BuildConfig
import com.leksin.app.data.local.TokenManager
import com.leksin.app.data.remote.model.ProblemDetail
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.coroutines.cancellation.CancellationException

object ApiClient {

    const val BASE_URL_EMULATOR = "http://10.0.2.2:8080/"
    const val BASE_URL_PRODUCTION = "https://leksin-api.duckdns.org/"

    /** Default dari BuildConfig: debug -> emulator, release -> production. */
    @Volatile
    var currentBaseUrl: String = BuildConfig.BASE_URL
        private set

    private val lock = Any()
    private val gson = Gson()

    @Volatile private var apiService: LeksinApi? = null
    @Volatile private var tokenManagerInstance: TokenManager? = null

    /** Satu instance TokenManager dipakai bersama oleh interceptor dan repository. */
    fun tokenManager(context: Context): TokenManager =
        tokenManagerInstance ?: synchronized(lock) {
            tokenManagerInstance
                ?: TokenManager(context.applicationContext).also { tokenManagerInstance = it }
        }

    fun getApiService(context: Context): LeksinApi =
        apiService ?: synchronized(lock) {
            apiService ?: buildRetrofit(context).create(LeksinApi::class.java).also {
                apiService = it
            }
        }

    /**
     * Khusus debug: arahin ke backend di LAN, mis. setCustomLanIp("192.168.1.50")..
     */
    fun setCustomLanIp(ipAddress: String, port: Int = 8080) {
        if (!BuildConfig.DEBUG) return
        val cleanIp = ipAddress.trim()
            .removePrefix("http://")
            .removePrefix("https://")
            .removeSuffix("/")
        setBaseUrl("http://$cleanIp:$port/")
    }

    fun setBaseUrl(url: String) {
        synchronized(lock) {
            currentBaseUrl = if (url.endsWith("/")) url else "$url/"
            apiService = null
        }
    }

    private fun buildRetrofit(context: Context): Retrofit {
        val tokenManager = tokenManager(context)

        // BODY akan mencetak password, OTP, dan JWT ke logcat -> jangan dipakai.
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BASIC
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
            redactHeader("Authorization")
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(currentBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Membungkus panggilan API menjadi [NetworkResult].
     * CancellationException WAJIB dilempar ulang supaya coroutine yang dibatalkan
     * (mis. layar ditutup) benar-benar berhenti.
     */
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkResult<T> {
        return try {
            NetworkResult.Success(apiCall())
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            NetworkResult.Error(
                statusCode = e.code(),
                problemDetail = parseProblemDetail(e)
            )
        } catch (e: Exception) {
            NetworkResult.Exception(e)
        }
    }

    /** Body error bisa kosong (mis. 401 dari filter keamanan) atau bukan JSON -> null. */
    private fun parseProblemDetail(httpException: HttpException): ProblemDetail? {
        return try {
            val body = httpException.response()?.errorBody()?.string()
            if (body.isNullOrBlank()) null else gson.fromJson(body, ProblemDetail::class.java)
        } catch (e: Exception) {
            null
        }
    }
}