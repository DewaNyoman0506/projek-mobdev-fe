package com.leksin.app.data.remote

import com.leksin.app.data.local.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (isPublicEndpoint(originalRequest.url.encodedPath)) {
            return chain.proceed(originalRequest)
        }

        if (!tokenManager.isLoggedIn()) {
            return chain.proceed(originalRequest)
        }

        val token = tokenManager.getToken()
            ?: return chain.proceed(originalRequest)

        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        return chain.proceed(authenticatedRequest)
    }

    private fun isPublicEndpoint(path: String): Boolean {
        return path in PUBLIC_ENDPOINTS
    }

    companion object {
        private val PUBLIC_ENDPOINTS = setOf(
            "/api/parent/register",
            "/api/parent/login",
            "/api/parent/auth/request-otp",
            "/api/parent/auth/verify-otp",
            "/api/child/auth/request-otp",
            "/api/child/auth/verify-otp",
        )
    }
}