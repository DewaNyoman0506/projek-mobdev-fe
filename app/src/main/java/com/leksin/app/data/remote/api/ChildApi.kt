package com.leksin.app.data.remote.api

import com.leksin.app.data.remote.dto.request.ChildCreateRequest
import com.leksin.app.data.remote.dto.request.ChildUpdateRequest
import com.leksin.app.data.remote.dto.response.ChildResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ChildApi {
    @PUT("parent/children/{childId}")
    suspend fun updateChildById(
        @Path("childId") childId: String,
        @Body request: ChildUpdateRequest,
    ): ChildResponse

    @DELETE("parent/children/{childId}")
    suspend fun deleteChildById(
        @Path("childId") childId: String,
    )

    @GET("parent/children")
    suspend fun getChildren(): List<ChildResponse>

    @POST("parent/children")
    suspend fun register(
        @Body request: ChildCreateRequest,
    ): ChildResponse
}