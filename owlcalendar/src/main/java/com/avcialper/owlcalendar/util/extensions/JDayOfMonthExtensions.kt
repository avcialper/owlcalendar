package com.avcialper.owlcalendar.util.extensions

import com.avcialper.jdatetime.model.JDayOfMonth

/**
 * Add empty days the list for first day of the month, and add empty days to the end of the list for
 * standard view high.
 * @return List of nullable [JDayOfMonth] objects.
 */
fun List<JDayOfMonth>.adjustDay(): List<JDayOfMonth?> {
    val firstDayIndex = this[0].dayOfWeek
    val emptyDays = MutableList<JDayOfMonth?>(firstDayIndex) { null }
    emptyDays.addAll(this)

    val dayCount = emptyDays.size
    if (dayCount < 36) {
        val diff = 36 - dayCount
        val days = List(diff) { null }
        emptyDays.addAll(days)
    }

    return emptyDays
}