package com.avcialper.owlcalendar.data.models

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

    fun isEqual(other: Date?): Boolean {
        return year == other?.year && month == other.month && dayOfMonth == other.dayOfMonth
    }
}