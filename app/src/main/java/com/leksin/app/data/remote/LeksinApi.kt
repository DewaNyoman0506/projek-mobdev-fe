package com.leksin.app.data.remote

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
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LeksinApi {

    @POST("api/parent/register")
    suspend fun registerParent(
        @Body request: ParentRegisterRequest
    ): ParentResponse

    @POST("api/parent/login")
    suspend fun loginParent(
        @Body request: ParentLoginRequest
    ): AuthResponse

    @GET("api/parent/{username}")
    suspend fun getParentProfile(
        @Path("username") username: String
    ): ParentResponse

    @GET("api/parent/children")
    suspend fun getChildren(): List<ChildResponse>

    @POST("api/parent/children")
    suspend fun createChild(
        @Body request: ChildCreateRequest
    ): ChildResponse

    @DELETE("api/parent/children/{childId}")
    suspend fun deleteChild(
        @Path("childId") childId: String
    ): Response<Unit>

    @POST("api/child/auth/request-otp")
    suspend fun requestChildOtp(
        @Body request: ChildOtpRequest
    ): MessageResponse

    @POST("api/child/auth/verify-otp")
    suspend fun verifyChildOtp(
        @Body request: ChildOtpVerifyRequest
    ): AuthResponse

    @POST("api/child/{childId}/games/sessions")
    suspend fun recordGameSession(
        @Path("childId") childId: String,
        @Body request: GameSessionRequest
    ): GameHistoryResponse

    @GET("api/child/{childId}/games/history")
    suspend fun getGameHistory(
        @Path("childId") childId: String
    ): List<GameHistoryResponse>

    @GET("api/child/{childId}/games/progress")
    suspend fun getGameProgress(
        @Path("childId") childId: String
    ): GameProgressResponse
}
