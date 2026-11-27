package com.leksin.app.data.remote.api

import retrofit2.http.POST

interface AuthApi {
    @POST("auth/logout")
    suspend fun logout()
}