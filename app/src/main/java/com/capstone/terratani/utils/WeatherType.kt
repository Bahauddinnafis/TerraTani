package com.capstone.terratani.utils

enum class WeatherType(val type: String, val translatedType: String) {
    THUNDERSTORM("Thunderstorm", "Badai"),
    DRIZZLE("Drizzle", "Gerimis"),
    RAIN("Rain", "Hujan"),
    SNOW("Snow", "Bersalju"),
    CLEAR("Clear", "Cerah"),
    CLOUDS("Clouds", "Berawan"),
    MIST("Mist", "Berkabut"),
    SMOKE("Smoke", "Berasap"),
    HAZE("Haze", "Berkabut"),
    DUST("Dust", "Puting Beliung Debu"),
    FOG("Fog", "Kabut"),
    SAND("Sand", "Pasir"),
    VOLCANIC_ASH("Ash", "Abu Vulkanik"),
    SQUALL("Squall", "Angin Kencang"),
    TORNADO("Tornado", "Tornado")
}