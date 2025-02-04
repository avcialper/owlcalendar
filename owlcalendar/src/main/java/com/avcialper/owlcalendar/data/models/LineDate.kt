package com.avcialper.owlcalendar.data.models

import com.avcialper.jdatetime.model.JDayOfMonth

data class LineSelectedDate(
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
)

data class LineDate(
    val startDate: LineSelectedDate,
    val endDate: LineSelectedDate,
    val color: Int
) {

    fun isStart(date: JDayOfMonth): Boolean {
        return startDate.year == date.year && startDate.month == date.month && startDate.dayOfMonth == date.dayOfMonth
    }

    fun isEnd(date: JDayOfMonth): Boolean {
        return endDate.year == date.year && endDate.month == date.month && endDate.dayOfMonth == date.dayOfMonth
    }

    fun isInRange(date: JDayOfMonth): Boolean {
        val isAfter = isAfter(date)
        val isBefore = isBefore(date)
        return isAfter && isBefore
    }

    private fun isAfter(date: JDayOfMonth): Boolean {
        val (year, month, dayOfMonth) = startDate
        return date.year > year || (date.year == year && date.month > month) ||
                (date.year == year && date.month == month && date.dayOfMonth > dayOfMonth)
    }

    private fun isBefore(date: JDayOfMonth): Boolean {
        val (year, month, dayOfMonth) = endDate
        return date.year < year || (date.year == year && date.month < month) ||
                (date.year == year && date.month == month && date.dayOfMonth < dayOfMonth)
    }

}