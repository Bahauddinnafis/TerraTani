package com.capstone.terratani.data.remote.repository

import com.capstone.terratani.data.remote.response.Resource
import com.capstone.terratani.data.remote.service.WeatherApiService
import com.capstone.terratani.data.remote.service.WeatherRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WeatherRepository {
    private val retrofit: WeatherApiService = WeatherRetrofit.create()
    fun getCurrentWeather(lat: Double, lon: Double) = flow {
        emit(Resource.Loading())
        try {
            val weatherResponse = retrofit.getWeatherByCoordinate(lat, lon)
            emit(Resource.Success(weatherResponse))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}