package com.avcialper.owlcalendar.data.models

internal data class MonthAndYear(
    val year: Int,
    val month: Int,
) {
    /**
     * Get next month and year of this date.
     */
    fun next(): MonthAndYear {
        var newMonth = month + 1
        var newYear = year
        if (newMonth > 11) {
            newMonth = 0
            newYear++
        }
        return MonthAndYear(newYear, newMonth)
    }

    /**
     * Get previous month and year of this date.
     */
    fun prev(): MonthAndYear {
        var newMonth = month - 1
        var newYear = year
        if (newMonth < 0) {
            newMonth = 11
            newYear--
        }
        return MonthAndYear(newYear, newMonth)
    }

    /**
     * Fınd current date index in the list.
     * @param dates List of dates
     */
    fun findIndex(dates: List<MonthAndYear>): Int = dates.indexOf(this)
}

internal fun List<MonthAndYear>.findIndex(date: MarkedDay): Int = this.indexOfFirst {
    it.year == date.year && it.month == date.month
}