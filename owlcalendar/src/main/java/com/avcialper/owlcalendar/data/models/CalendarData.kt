package com.avcialper.owlcalendar.data.models

import com.avcialper.jdatetime.JDateTime
import com.avcialper.owlcalendar.util.constants.CalendarMode

internal class CalendarData {

    private val jDateTime = JDateTime.instance
    var selectedDate = SelectedDate(jDateTime.year, jDateTime.month, jDateTime.dayOfMonth, null)

    var startDate = YearAndMonth(jDateTime.year, jDateTime.month)

    private val markedDays = mutableListOf<MarkedDay>()
    private var onMarkedDayAddedListener: ((MarkedDay) -> Unit)? = null
    private var onDayUpdateListener: ((MarkedDay) -> Unit)? = null

    var lineDate: LineDate? = null
    private var onLineDateChangeListener: ((LineDate) -> Unit)? = null

    var calendarPosition = 1

    // attrs
    var selectedDateBackgroundColor: Int = 0
    var todayTextColor: Int = 0
    var dayTextColor: Int = 0
    var dayNameTextColor: Int = 0
    var dateTextColor: Int = 0
    var lineBackgroundColor: Int = 0
    var calendarMode: CalendarMode = CalendarMode.NORMAL

    fun setAttrs(
        selectedDateBackgroundColor: Int,
        todayTextColor: Int,
        dayTextColor: Int,
        dayNameTextColor: Int,
        dateTextColor: Int,
        lineBackgroundColor: Int,
        calendarMode: CalendarMode
    ) {
        this.selectedDateBackgroundColor = selectedDateBackgroundColor
        this.todayTextColor = todayTextColor
        this.dayTextColor = dayTextColor
        this.dayNameTextColor = dayNameTextColor
        this.dateTextColor = dateTextColor
        this.lineBackgroundColor = lineBackgroundColor
        this.calendarMode = calendarMode
    }


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
    fun setOnLineDateChangeListener(listener: (LineDate) -> Unit) {
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
