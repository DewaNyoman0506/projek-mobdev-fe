package com.leksin.app.data.remote.api

import com.leksin.app.data.remote.dto.request.ParentLoginRequest
import com.leksin.app.data.remote.dto.request.ParentRegisterRequest
import com.leksin.app.data.remote.dto.request.ParentUpdateRequest
import com.leksin.app.data.remote.dto.response.AuthResponse
import com.leksin.app.data.remote.dto.response.ParentResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ParentApi {

    @POST("parent/register")
    suspend fun register(
        @Body request: ParentRegisterRequest,
    ): ParentResponse

    @POST("parent/login")
    suspend fun login(
        @Body request: ParentLoginRequest,
    ): AuthResponse

    @GET("parent/{username}")
    suspend fun getProfile(
        @Path("username") username: String,
    ): ParentResponse

    @PATCH("parent/profile")
    suspend fun updateProfile(
        @Body request: ParentUpdateRequest,
    ): ParentResponse
}