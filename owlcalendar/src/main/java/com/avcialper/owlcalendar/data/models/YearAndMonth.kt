package com.avcialper.owlcalendar.data.models

internal data class YearAndMonth(
    val year: Int,
    val month: Int,
) {
    /**
     * Get next month and year of this date.
     */
    fun next(): YearAndMonth {
        var newMonth = month + 1
        var newYear = year
        if (newMonth > 11) {
            newMonth = 0
            newYear++
        }
        return YearAndMonth(newYear, newMonth)
    }

    /**
     * Get previous month and year of this date.
     */
    fun prev(): YearAndMonth {
        var newMonth = month - 1
        var newYear = year
        if (newMonth < 0) {
            newMonth = 11
            newYear--
        }
        return YearAndMonth(newYear, newMonth)
    }

    /**
     * Fınd current date index in the list.
     * @param dates List of dates
     */
    fun findIndex(dates: List<YearAndMonth>): Int = dates.indexOf(this)
}

internal fun List<YearAndMonth>.findIndex(date: MarkedDay): Int = this.indexOfFirst {
    it.year == date.year && it.month == date.month
}

internal fun List<YearAndMonth>.findStartIndex(lineDate: LineDate): Int = this.indexOfFirst {
    it.year == lineDate.startDate.year && it.month == lineDate.startDate.month
}

internal fun List<YearAndMonth>.findEndIndex(lineDate: LineDate): Int = this.indexOfFirst {
    it.year == lineDate.endDate.year && it.month == lineDate.endDate.month
}