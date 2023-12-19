package com.capstone.terratani.ui.fragments.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.capstone.terratani.data.remote.repository.WeatherRepository

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val weatherRepository = WeatherRepository()
    fun getWeatherByCoordinate(lat: Double = 0.0, lon: Double = 0.0) = weatherRepository.getCurrentWeather(lat, lon).asLiveData()
}