package com.avcialper.owlcalendar.util.constants

import java.text.DateFormatSymbols
import java.util.Locale

internal object Constants {
    fun getLocalizedMonthNames(): List<String> {
        val symbols = DateFormatSymbols(Locale.getDefault())
        return symbols.months.filter { it.isNotEmpty() }
    }

    fun getLocalizedDayNames(): List<String> {
        val symbols = DateFormatSymbols(Locale.getDefault())
        val weekdays = symbols.shortWeekdays
        return weekdays.drop(2).plus(weekdays[1])
    }
}