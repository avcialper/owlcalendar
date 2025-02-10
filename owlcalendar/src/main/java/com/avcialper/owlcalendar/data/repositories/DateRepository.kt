package com.avcialper.owlcalendar.data.repositories

import com.avcialper.jdatetime.JDateTime
import com.avcialper.owlcalendar.data.models.Date
import com.avcialper.owlcalendar.data.models.MonthAndYear
import com.avcialper.owlcalendar.data.models.adjustDay
import com.avcialper.owlcalendar.util.extensions.toDateList

internal class DateRepository {

    private val jDateTime = JDateTime.instance

    /**
     * Initializes a date list containing the dates for yesterday, today, and tomorrow.
     * @param year The year of the calendar.
     * @param month The month of the calendar.
     * @return A list of dates in the order: yesterday, today, tomorrow.
     */
    fun getStartValues(year: Int, month: Int): List<MonthAndYear> {
        val today = MonthAndYear(year, month)
        val yesterday = today.prev()
        val tomorrow = today.next()

        return listOf(yesterday, today, tomorrow)
    }

    /**
     * Get days of the month.
     * @param year Year of the calendar
     * @param month Month of the calendar
     * @return List of [Date] objects.
     */
    fun getDays(year: Int, month: Int): List<Date?> {
        val daysInMonth = jDateTime.getAllDaysOfMonth(year, month)
        val dateList = daysInMonth.toDateList()
        return dateList.adjustDay()
    }


}