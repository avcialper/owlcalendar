package com.avcialper.owlcalendar.util.extensions

import com.avcialper.jdatetime.model.JDate
import com.avcialper.owlcalendar.data.models.MarkedDay
import com.avcialper.owlcalendar.data.models.SelectedDate

/**
 * Add empty days the list for first day of the month, and add empty days to the end of the list for
 * standard view high.
 * @return List of nullable [JDate] objects.
 */
internal fun List<JDate>.adjustDay(): List<JDate?> {
    val firstDayIndex = this[0].dayOfWeek
    val emptyDays = MutableList<JDate?>(firstDayIndex) { null }
    emptyDays.addAll(this)

    val dayCount = emptyDays.size
    if (dayCount < 36) {
        val diff = 36 - dayCount
        val days = List(diff) { null }
        emptyDays.addAll(days)
    }

    return emptyDays
}

/**
 * Find marked day by date.
 * @param date Date of the marked day
 */
internal fun List<JDate?>.findMarkedDay(date: MarkedDay): JDate? = this.find {
    it?.year == date.year && it.month == date.month && it.dayOfMonth == date.dayOfMonth
}

/**
 * Find selected date index.
 * @param day Selected date
 */
internal fun List<JDate?>.findIndex(day: SelectedDate): Int = this.indexOfFirst {
    it?.year == day.year && it.month == day.month && it.dayOfMonth == day.dayOfMonth
}

/**
 * Find selected date index.
 * @param day Selected date
 */
internal fun List<JDate?>.findIndex(day: JDate): Int = this.indexOfFirst {
    it?.year == day.year && it.month == day.month && it.dayOfMonth == day.dayOfMonth
}

/**
 * Find selected date index.
 * @param day Selected date
 */
internal fun List<JDate?>.findIndex(day: MarkedDay): Int = this.indexOfFirst {
    it?.year == day.year && it.month == day.month && it.dayOfMonth == day.dayOfMonth
}