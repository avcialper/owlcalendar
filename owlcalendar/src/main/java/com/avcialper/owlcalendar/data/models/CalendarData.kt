package com.avcialper.owlcalendar.data.models

import com.avcialper.jdatetime.JDateTime

internal class CalendarData {

    private val jDateTime = JDateTime.instance
    var selectedDate = SelectedDate(jDateTime.year, jDateTime.month, jDateTime.dayOfMonth, null)

    var startDate = StartDate(jDateTime.year, jDateTime.month, jDateTime.dayOfMonth)
    var onStartDateChangeListener: (() -> Unit)? = null

    private val markedDays = mutableListOf<MarkedDay>()
    private var onMarkedDayAddedListener: ((MarkedDay) -> Unit)? = null
    private var onDayUpdateListener: ((MarkedDay) -> Unit)? = null

    var lineDate: LineDate? = null
        set(value) {
            field = value
            onLineDateChangeListener?.invoke(value)
        }
    private var onLineDateChangeListener: ((LineDate?) -> Unit)? = null

    var calendarPosition = 1

    /**
     * Add new marked days to the list.
     * @param newDays New marked days
     */
    fun addMarkedDays(newDays: List<MarkedDay>) {
        newDays.forEach { date ->
            addMarkedDay(date)
        }
    }

    /**
     * Add new marked day to the list.
     * @param date New marked day
     */
    fun addMarkedDay(date: MarkedDay) {
        val existingDay = findMarkedDay(date)
        if (existingDay != null) {
            if (existingDay.color == date.color) return
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
     * @param date Date of the marked day
     */
    fun findMarkedDay(date: Date): MarkedDay? = markedDays.findMarkedDay(date)

    /**
     * Set listener for new marked day.
     */
    fun setOnMarkedDayAddedListener(listener: (MarkedDay) -> Unit) {
        onMarkedDayAddedListener = listener
    }

    /**
     * Set listener for day update.
     */
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
     * Set listener for new line date.
     * @param listener Listener for new line date
     */
    fun setOnLineDateChangeListener(listener: (LineDate?) -> Unit) {
        onLineDateChangeListener = listener
    }

    /**
     * Clear line date.
     */
    fun clearLineDate() {
        val oldLineDate = lineDate
        lineDate = null

        if (oldLineDate != null)
            onLineDateChangeListener?.invoke(oldLineDate)
    }

    /**
     * Clear marked days.
     */
    fun clearMarkedDays() {
        val oldMarkedDays = markedDays
        markedDays.clear()

        if (oldMarkedDays.isNotEmpty())
            oldMarkedDays.forEach {
                onMarkedDayAddedListener?.invoke(it)
            }
    }
}
