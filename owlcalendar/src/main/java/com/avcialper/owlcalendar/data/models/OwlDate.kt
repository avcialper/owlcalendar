package com.avcialper.owlcalendar.data.models

internal data class OwlDate(
    val year: Int,
    val month: Int,
) {
    /**
     * Get next month and year of this date.
     */
    fun next(): OwlDate {
        var newMonth = month + 1
        var newYear = year
        if (newMonth > 11) {
            newMonth = 0
            newYear++
        }
        return OwlDate(newYear, newMonth)
    }

    /**
     * Get previous month and year of this date.
     */
    fun prev(): OwlDate {
        var newMonth = month - 1
        var newYear = year
        if (newMonth < 0) {
            newMonth = 11
            newYear--
        }
        return OwlDate(newYear, newMonth)
    }

    /**
     * Fınd current date index in the list.
     * @param dates List of dates
     */
    fun findIndex(dates: List<OwlDate>): Int = dates.indexOf(this)
}