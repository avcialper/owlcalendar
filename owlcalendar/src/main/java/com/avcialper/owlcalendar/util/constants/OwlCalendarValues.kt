package com.avcialper.owlcalendar.util.constants

import com.avcialper.jdatetime.JDateTime
import com.avcialper.owlcalendar.data.models.MarkedDay
import com.avcialper.owlcalendar.data.models.SelectedDate

internal object OwlCalendarValues {

    var selectedDate: SelectedDate = SelectedDate(JDateTime.instance.date, 1)
    var markedDays: MutableList<MarkedDay> = mutableListOf()

    /**
     * Add new marked days to the list.
     * @param newDays New marked days
     */
    fun addMarkedDays(newDays: List<MarkedDay>) {
        markedDays.addAll(newDays)
    }

    /**
     * Add new marked day to the list.
     * @param newDay New marked day
     */
    fun addMarkedDay(newDay: MarkedDay) {
        markedDays.add(newDay)
    }

    /**
     * Find marked day by date.
     * @param date Date of the marked day
     */
    fun findMarkedDay(date: String): MarkedDay? {
        return markedDays.find { it.date == date }
    }

}