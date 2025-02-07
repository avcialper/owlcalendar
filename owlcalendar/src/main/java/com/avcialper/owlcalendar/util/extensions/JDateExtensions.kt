package com.avcialper.owlcalendar.util.extensions

import com.avcialper.jdatetime.model.JDate
import com.avcialper.owlcalendar.data.models.Date

/**
 * Convert [JDate] to [Date].
 */
internal fun JDate.toDate(): Date {
    val (date, dayOfMonth, dayName, dayOfWeek, month, year) = this
    return Date(date, dayOfMonth, dayName, dayOfWeek, month, year)
}

/**
 * Convert [JDate] list to [Date] list.
 */
internal fun List<JDate>.toDateList(): List<Date> = this.map { it.toDate() }