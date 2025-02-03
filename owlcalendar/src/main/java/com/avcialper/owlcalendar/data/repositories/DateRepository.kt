package com.avcialper.owlcalendar.data.repositories

import com.avcialper.jdatetime.JDateTime
import com.avcialper.jdatetime.model.JDayOfMonth
import com.avcialper.owlcalendar.data.models.MonthAndYear
import com.avcialper.owlcalendar.util.extensions.adjustDay

internal class DateRepository {

    private val jDateTime = JDateTime.instance

    /**
     * Initializes a date list containing the dates for yesterday, today, and tomorrow.
     *
     * The current date is determined using the `JDateTime` instance. Based on this:
     * - `yesterday` is calculated as the previous date from today.
     * - `today` represents the current date (year and month).
     * - `tomorrow` is calculated as the next date from today.
     *
     * @return A list of dates in the order: yesterday, today, tomorrow.
     */
    fun getStartValues(): List<MonthAndYear> {
        val jDateTime = JDateTime.instance
        val currentYear = jDateTime.year
        val currentMonth = jDateTime.month

        val today = MonthAndYear(currentYear, currentMonth)
        val yesterday = today.prev()
        val tomorrow = today.next()

        return listOf(yesterday, today, tomorrow)
    }

    /**
     * Get days of the month.
     * @param year Year of the calendar
     * @param month Month of the calendar
     * @return List of [JDayOfMonth] objects.
     */
    fun getDays(year: Int, month: Int): List<JDayOfMonth?> {
        val daysInMonth = jDateTime.getAllDaysOfMonth(year, month).adjustDay()
        return daysInMonth
    }


}