package com.avcialper.owlcalendar.data.models

import com.avcialper.jdatetime.model.JDayOfMonth

internal data class SelectedDate(
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
    var calendarPosition: Int
) {
    /**
     * Increment calendar position.
     */
    fun increasePosition() {
        calendarPosition++
    }

    fun isEqual(other: JDayOfMonth?): Boolean {
        return year == other?.year && month == other.month && dayOfMonth == other.dayOfMonth
    }
}