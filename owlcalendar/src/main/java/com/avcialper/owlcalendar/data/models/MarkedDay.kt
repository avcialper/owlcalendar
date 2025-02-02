package com.avcialper.owlcalendar.data.models

import com.avcialper.jdatetime.model.JDayOfMonth

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

internal fun List<MarkedDay>.findMarkedDay(jDayOfMonth: JDayOfMonth): MarkedDay? = this.find {
    it.year == jDayOfMonth.year && it.month == jDayOfMonth.month && it.dayOfMonth == jDayOfMonth.dayOfMonth
}