package com.avcialper.owlcalendar.util.constants

import com.avcialper.jdatetime.JDateTime
import com.avcialper.jdatetime.model.JDayOfMonth
import com.avcialper.owlcalendar.data.models.MarkedDay
import com.avcialper.owlcalendar.data.models.SelectedDate
import com.avcialper.owlcalendar.data.models.findMarkedDay

internal object CalendarValues {

    private val jDateTime: JDateTime = JDateTime.instance
    var selectedDate: SelectedDate? =
        SelectedDate(jDateTime.year, jDateTime.month, jDateTime.dayOfMonth, 1)

    private val markedDays: MutableList<MarkedDay> = mutableListOf()
    private var onMarkedDayAddedListener: ((MarkedDay) -> Unit)? = null
    private var onDayUpdateListener: ((MarkedDay) -> Unit)? = null

    var calendarPosition = 1

    /**
     * Add new marked days to the list.
     * @param newDays New marked days
     */
    fun addMarkedDays(newDays: List<MarkedDay>) {
        newDays.forEach(::addMarkedDay)
    }

    /**
     * Add new marked day to the list.
     * @param date New marked day
     */
    fun addMarkedDay(date: MarkedDay) {
        val existingDay = findMarkedDay(date)
        if (existingDay != null) {
            existingDay.color = date.color
        } else {
            markedDays.add(date)
        }
        onMarkedDayAddedListener?.invoke(date)
    }

    /**
     * Find marked day by date.
     * @param date Date of the marked day
     */
    fun findMarkedDay(date: MarkedDay): MarkedDay? = markedDays.findMarkedDay(date)

    /**
     * Find marked day by date.
     * @param jDayOfMonth Date of the marked day
     */
    fun findMarkedDay(jDayOfMonth: JDayOfMonth): MarkedDay? = markedDays.findMarkedDay(jDayOfMonth)

    fun setOnMarkedDayAddedListener(listener: (MarkedDay) -> Unit) {
        onMarkedDayAddedListener = listener
    }

    fun setOnDayUpdateListener(listener: (MarkedDay) -> Unit) {
        onDayUpdateListener = listener
    }

    /**
     * Update marked day.
     * @param markedDay Marked day
     */
    fun onDayUpdate(markedDay: MarkedDay) {
        onDayUpdateListener?.invoke(markedDay)
    }

    /**
     * Clear states when app is closed.
     */
    fun clear() {
        selectedDate = null
        markedDays.clear()
        onMarkedDayAddedListener = null
        calendarPosition = 1
    }
}