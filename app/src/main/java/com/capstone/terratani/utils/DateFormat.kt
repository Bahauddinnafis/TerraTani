package com.capstone.terratani.utils

enum class DateFormat(val format: String) {
    DATE("dd MMMM yyyy"),
    DATE_TIME("${DATE.format}, HH:mm"),
    DAY_DATE("EEEE, dd MMMM yyyy"),
    DAY_DATE_TIME("${DAY_DATE.format}, HH:mm"),
    TIME("HH:mm")
}