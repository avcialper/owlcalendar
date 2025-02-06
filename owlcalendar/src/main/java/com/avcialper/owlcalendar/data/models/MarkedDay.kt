package com.avcialper.owlcalendar.data.models

import com.avcialper.jdatetime.model.JDate

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

internal fun List<MarkedDay>.findMarkedDay(jDate: JDate): MarkedDay? = this.find {
    it.year == jDate.year && it.month == jDate.month && it.dayOfMonth == jDate.dayOfMonth
}