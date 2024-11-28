package com.lonwulf.budgetapp.util

import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.TextStyle
import java.time.temporal.ChronoField
import java.util.Locale

fun thousandFormatter(number: Double, decimalPlaces: Int = 2): String {
    val formatter = DecimalFormat(
        "#,###." + "0".repeat(decimalPlaces),
        DecimalFormatSymbols(Locale.getDefault())
    )
    return formatter.format(number)
}
fun getLocalTime(): String {
    val currentTime = LocalTime.now(ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return currentTime.format(formatter)
}
fun getFormattedDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatterBuilder()
        .appendPattern("d")
        .appendText(
            ChronoField.DAY_OF_MONTH, mapOf(
                1L to "st", 2L to "nd", 3L to "rd", 4L to "th",
                5L to "th",6L to "th",7L to "th",8L to "th",9L to "th",10L to "th",
                11L to "th",12L to "th",13L to "th",14L to "th",15L to "th",
                16L to "th",17L to "th",18L to "th",19L to "th",20L to "th",
                21L to "st", 22L to "nd", 23L to "rd", 24L to "th",
                25L to "th",26L to "th",27L to "th",28L to "th",29L to "th",30L to "th",
                31L to "st"
            ))
        .appendPattern(" MMM yyyy")
        .toFormatter(Locale.ENGLISH)
    return currentDate.format(formatter)
}

fun getLocalDateMonth(): String {
    val currentDate = LocalDate.now()
    val currentMonth = currentDate.month
    return currentMonth.getDisplayName(TextStyle.FULL, Locale.getDefault())
}