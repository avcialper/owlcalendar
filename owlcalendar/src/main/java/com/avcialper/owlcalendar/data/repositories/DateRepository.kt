package com.avcialper.owlcalendar.data.repositories

import com.avcialper.jdatetime.JDateTime
import com.avcialper.owlcalendar.data.models.Date
import com.avcialper.owlcalendar.data.models.YearAndMonth
import com.avcialper.owlcalendar.data.models.adjustDay
import com.avcialper.owlcalendar.helper.CalendarManager.locale
import com.avcialper.owlcalendar.util.extensions.toDateList
import java.text.DateFormatSymbols

internal class DateRepository {

    companion object {
        private val jDateTime = JDateTime.instance

        /**
         * Initializes a date list containing the dates for yesterday, today, and tomorrow.
         * @param year The year of the calendar.
         * @param month The month of the calendar.
         * @return A list of dates in the order: yesterday, today, tomorrow.
         */
        fun getStartValues(year: Int, month: Int): List<YearAndMonth> {
            val today = YearAndMonth(year, month)
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

        /**
         * Get localized month names.
         * @return List of localized month names.
         */
        fun getLocalizedMonthNames(): List<String> {
            val symbols = DateFormatSymbols(locale)
            return symbols.months.filter { it.isNotEmpty() }
        }

        /**
         * Get localized day names.
         * @return List of localized day names. Monday - Sunday
         */
        fun getLocalizedDayNames(): List<String> {
            val symbols = DateFormatSymbols(locale)
            val weekdays = symbols.shortWeekdays
            return weekdays.drop(2).plus(weekdays[1])
        }
    }
}