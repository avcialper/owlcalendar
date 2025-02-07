package com.avcialper.owlcalendar.data.models

data class LineSelectedDate(
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
)

data class LineDate(
    val startDate: LineSelectedDate,
    val endDate: LineSelectedDate,
) {

    fun isStart(date: Date): Boolean {
        return startDate.year == date.year && startDate.month == date.month && startDate.dayOfMonth == date.dayOfMonth
    }

    fun isEnd(date: Date): Boolean {
        return endDate.year == date.year && endDate.month == date.month && endDate.dayOfMonth == date.dayOfMonth
    }

    fun isInRange(date: Date): Boolean {
        val isAfter = isAfter(date)
        val isBefore = isBefore(date)
        return isAfter && isBefore
    }

    private fun isAfter(date: Date): Boolean {
        val (year, month, dayOfMonth) = startDate
        return date.year > year || (date.year == year && date.month > month) ||
                (date.year == year && date.month == month && date.dayOfMonth > dayOfMonth)
    }

    private fun isBefore(date: Date): Boolean {
        val (year, month, dayOfMonth) = endDate
        return date.year < year || (date.year == year && date.month < month) ||
                (date.year == year && date.month == month && date.dayOfMonth < dayOfMonth)
    }

}