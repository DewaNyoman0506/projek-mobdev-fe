package com.leksin.app.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.leksin.app.BuildConfig
import com.leksin.app.data.local.TokenManager
import com.leksin.app.data.remote.AuthInterceptor
import com.leksin.app.data.remote.api.AuthApi
import com.leksin.app.data.remote.api.ChildApi
import com.leksin.app.data.remote.api.ChildOtpApi
import com.leksin.app.data.remote.api.GameApi
import com.leksin.app.data.remote.api.ParentApi
import com.leksin.app.data.remote.api.ParentOtpApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTokenManager(
        @ApplicationContext context: Context,
    ): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BASIC
            } else {
                HttpLoggingInterceptor.Level.NONE
            }

            redactHeader("Authorization")
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(gson)
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideParentApi(
        retrofit: Retrofit,
    ): ParentApi {
        return retrofit.create(ParentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideParentOtpApi(
        retrofit: Retrofit,
    ): ParentOtpApi {
        return retrofit.create(ParentOtpApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChildApi(
        retrofit: Retrofit,
    ): ChildApi {
        return retrofit.create(ChildApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChildOtpApi(
        retrofit: Retrofit,
    ): ChildOtpApi {
        return retrofit.create(ChildOtpApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGameApi(
        retrofit: Retrofit,
    ): GameApi {
        return retrofit.create(GameApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApi(
        retrofit: Retrofit,
    ): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}