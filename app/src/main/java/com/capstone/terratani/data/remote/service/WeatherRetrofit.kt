package com.capstone.terratani.data.remote.service

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object WeatherRetrofit {
    // Create client
    private fun client(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor {
                val original = it.request()
                val requestBuilder = original.newBuilder()
                val request = requestBuilder.build()
                it.proceed(request)
            }
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

    // Create Retrofit API Service
    fun create(): WeatherApiService =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(client())
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(WeatherApiService::class.java)
}