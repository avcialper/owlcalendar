package com.avcialper.owlcalendar.data.models

/**
 * Data class for marked days.
 * @param year Year of the marked day
 * @param month Month of the marked day [0-11]
 * @param dayOfMonth Day of the month
 * @param color Color of the marked day
 */
data class MarkedDay(
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
    var color: Int
)

internal fun List<MarkedDay>.findMarkedDay(markedDay: MarkedDay): MarkedDay? = this.find {
    it.year == markedDay.year && it.month == markedDay.month && it.dayOfMonth == markedDay.dayOfMonth
}

internal fun List<MarkedDay>.findMarkedDay(date: Date): MarkedDay? = this.find {
    it.year == date.year && it.month == date.month && it.dayOfMonth == date.dayOfMonth
}