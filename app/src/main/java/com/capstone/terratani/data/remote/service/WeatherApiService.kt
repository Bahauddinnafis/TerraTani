package com.capstone.terratani.data.remote.service

import com.capstone.terratani.data.remote.response.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather?appid=ed2925d7941f698c766e7b79847cd1fa&lang=id&units=metric")
    suspend fun getWeatherByCoordinate(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
    ): WeatherResponse
}