package com.capstone.terratani.data.remote.response.weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val coord: Coord? = null,
    val weather: List<Weather>?  = null,
    val base: String? = null,
    val main: Main? = null,
    val visibility: Int = 0,
    val wind: Wind? = null,
    val rain: Rain? = null,
    val clouds: Clouds? = null,
    val dt: Long = 0L,
    val sys: Sys? = null,
    val timezone: Int = 0,
    val id: Int = 0,
    val name: String = "",
    val cod: Int = 0
)

@Serializable
data class Coord(
    val lon: Double = 0.0,
    val lat: Double = 0.0
)

@Serializable
data class Weather(
    val id: Int = 0,
    val main: String = "",
    val description: String = "",
    val icon: String = ""
)

@Serializable
data class Main(
    val temp: Double = 0.0,
    @SerialName("feels_like") val feelsLike: Double = 0.0,
    @SerialName("temp_min") val tempMin: Double = 0.0,
    @SerialName("temp_max") val tempMax: Double = 0.0,
    val pressure: Int = 0,
    val humidity: Int = 0,
    @SerialName("sea_level") val seaLevel: Int = 0,
    @SerialName("grnd_level") val groundLevel: Int = 0
)

@Serializable
data class Wind(
    val speed: Double = 0.0,
    val deg: Int = 0,
    val gust: Double = 0.0
)

@Serializable
data class Rain(
    @SerialName("1h") val oneHour: Double = 0.0
)

@Serializable
data class Clouds(
    val all: Int = 0
)

@Serializable
data class Sys(
    val type: Int = 0,
    val id: Int = 0,
    val country: String = "",
    val sunrise: Long = 0L,
    val sunset: Long = 0L
)