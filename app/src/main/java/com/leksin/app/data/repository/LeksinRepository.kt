package com.leksin.app.data.repository

import android.content.Context
import com.leksin.app.data.local.TokenManager
import com.leksin.app.data.remote.ApiClient
import com.leksin.app.data.remote.LeksinApi
import com.leksin.app.data.remote.NetworkResult
import com.leksin.app.data.remote.model.AuthResponse
import com.leksin.app.data.remote.model.ChildCreateRequest
import com.leksin.app.data.remote.model.ChildOtpRequest
import com.leksin.app.data.remote.model.ChildOtpVerifyRequest
import com.leksin.app.data.remote.model.ChildResponse
import com.leksin.app.data.remote.model.GameHistoryResponse
import com.leksin.app.data.remote.model.GameProgressResponse
import com.leksin.app.data.remote.model.GameSessionRequest
import com.leksin.app.data.remote.model.MessageResponse
import com.leksin.app.data.remote.model.ParentLoginRequest
import com.leksin.app.data.remote.model.ParentRegisterRequest
import com.leksin.app.data.remote.model.ParentResponse
import retrofit2.Response

class LeksinRepository(context: Context) {

    private val api: LeksinApi = ApiClient.getApiService(context)
    val tokenManager: TokenManager = TokenManager(context.applicationContext)

    // --- Endpoint 4 Parent Auth---

    suspend fun registerParent(
        username: String,
        password: String,
        email: String
    ): NetworkResult<ParentResponse> {
        return ApiClient.safeApiCall {
            api.registerParent(ParentRegisterRequest(username, password, email))
        }
    }

    suspend fun loginParent(
        username: String,
        password: String
    ): NetworkResult<AuthResponse> {
        val result = ApiClient.safeApiCall {
            api.loginParent(ParentLoginRequest(username, password))
        }
        if (result is NetworkResult.Success) {
            saveSession(result.data)
        }
        return result
    }

    suspend fun getParentProfile(username: String): NetworkResult<ParentResponse> {
        return ApiClient.safeApiCall {
            api.getParentProfile(username)
        }
    }

    // --- Endpoint 4 Child Management ---

    suspend fun getChildren(): NetworkResult<List<ChildResponse>> {
        return ApiClient.safeApiCall {
            api.getChildren()
        }
    }

    suspend fun createChild(nickname: String): NetworkResult<ChildResponse> {
        return ApiClient.safeApiCall {
            api.createChild(ChildCreateRequest(nickname))
        }
    }

    suspend fun deleteChild(childId: String): NetworkResult<Response<Unit>> {
        return ApiClient.safeApiCall {
            api.deleteChild(childId)
        }
    }

    // --- OTP Anak ---

    suspend fun requestChildOtp(
        parentUsername: String,
        nickname: String
    ): NetworkResult<MessageResponse> {
        return ApiClient.safeApiCall {
            api.requestChildOtp(ChildOtpRequest(parentUsername, nickname))
        }
    }

    suspend fun verifyChildOtp(
        parentUsername: String,
        nickname: String,
        code: String
    ): NetworkResult<AuthResponse> {
        val result = ApiClient.safeApiCall {
            api.verifyChildOtp(ChildOtpVerifyRequest(parentUsername, nickname, code))
        }
        if (result is NetworkResult.Success) {
            saveSession(result.data)
        }
        return result
    }

    // --- Endpoint History, Game, dll ---

    suspend fun recordGameSession(
        childId: String,
        level: Int,
        durationSeconds: Int,
        attempts: Int,
        starsEarned: Int,
        completed: Boolean
    ): NetworkResult<GameHistoryResponse> {
        val request = GameSessionRequest(
            level = level,
            durationSeconds = durationSeconds,
            attempts = attempts,
            starsEarned = starsEarned,
            completed = completed
        )
        return ApiClient.safeApiCall {
            api.recordGameSession(childId, request)
        }
    }

    suspend fun getGameHistory(childId: String): NetworkResult<List<GameHistoryResponse>> {
        return ApiClient.safeApiCall {
            api.getGameHistory(childId)
        }
    }

    suspend fun getGameProgress(childId: String): NetworkResult<GameProgressResponse> {
        return ApiClient.safeApiCall {
            api.getGameProgress(childId)
        }
    }

    // --- Helper ---

    private fun saveSession(auth: AuthResponse) {
        tokenManager.saveAuthSession(
            token = auth.accessToken,
            userId = auth.userId,
            username = auth.username,
            role = auth.role,
            expiresInSeconds = auth.expiresIn
        )
    }

    fun logout() {
        tokenManager.clearSession()
    }
}
