package com.avcialper.owlcalendar.data.models

internal data class SelectedDate(
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
    var calendarPosition: Int?
) {
    /**
     * Checks if the date is same with the other date.
     * @param other The date to compare with.
     * @return `true` if the date is before the other date, `false` otherwise.
     */
    fun isEqual(other: Date?): Boolean {
        return year == other?.year && month == other.month && dayOfMonth == other.dayOfMonth
    }

    /**
     * Checks if the date is same with the other date.
     * @param other The date to compare with.
     * @return `true` if the date is before the other date, `false` otherwise.
     */
    fun isEqual(other: SelectedDate): Boolean {
        return year == other.year && month == other.month && dayOfMonth == other.dayOfMonth
    }

    /**
     * Checks if the date is before the other date.
     * @param other The date to compare with.
     * @return `true` if the date is before the other date, `false` otherwise.
     */
    fun isBefore(other: SelectedDate): Boolean {
        return year < other.year || (year == other.year && month < other.month) || (year == other.year && month == other.month && dayOfMonth < other.dayOfMonth)
    }

}