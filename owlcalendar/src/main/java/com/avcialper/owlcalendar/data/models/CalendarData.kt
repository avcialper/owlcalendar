package com.avcialper.owlcalendar.data.models

import com.avcialper.jdatetime.JDateTime
import com.avcialper.owlcalendar.util.constants.CalendarMode

internal class CalendarData {

    private val jDateTime = JDateTime.instance
    var selectedDate = SelectedDate(jDateTime.year, jDateTime.month, jDateTime.dayOfMonth, 1)

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
    var calendarMode: CalendarMode = CalendarMode.NORMAL

    fun setAttrs(
        selectedDateBackgroundColor: Int,
        todayTextColor: Int,
        dayTextColor: Int,
        dayNameTextColor: Int,
        dateTextColor: Int,
        calendarMode: CalendarMode
    ) {
        this.selectedDateBackgroundColor = selectedDateBackgroundColor
        this.todayTextColor = todayTextColor
        this.dayTextColor = dayTextColor
        this.dayNameTextColor = dayNameTextColor
        this.dateTextColor = dateTextColor
        this.calendarMode = calendarMode
    }

    companion object {

        /**
         * Add new marked days to the list.
         * @param instance Instance of the calendar
         * @param newDays New marked days
         */
        fun addMarkedDays(instance: CalendarData, newDays: List<MarkedDay>) {
            newDays.forEach { date ->
                addMarkedDay(instance, date)
            }
        }

        /**
         * Add new marked day to the list.
         * @param instance Instance of the calendar
         * @param date New marked day
         */
        fun addMarkedDay(instance: CalendarData, date: MarkedDay) {
            val existingDay = findMarkedDay(instance, date)
            if (existingDay != null) {
                if (existingDay.color == date.color) return
                existingDay.color = date.color
            } else {
                instance.markedDays.add(date)
            }
            instance.onMarkedDayAddedListener?.invoke(date)
        }

        /**
         * Find marked day by date.
         * @param instance Instance of the calendar
         * @param date Date of the marked day
         */
        fun findMarkedDay(instance: CalendarData, date: MarkedDay): MarkedDay? =
            instance.markedDays.findMarkedDay(date)

        /**
         * Find marked day by date.
         * @param instance Instance of the calendar
         * @param date Date of the marked day
         */
        fun findMarkedDay(instance: CalendarData, date: Date): MarkedDay? =
            instance.markedDays.findMarkedDay(date)

        /**
         * Set listener for new marked day.
         * @param instance Instance of the calendar
         */
        fun setOnMarkedDayAddedListener(instance: CalendarData, listener: (MarkedDay) -> Unit) {
            instance.onMarkedDayAddedListener = listener
        }

        /**
         * Set listener for day update.
         * @param instance Instance of the calendar
         */
        fun setOnDayUpdateListener(instance: CalendarData, listener: (MarkedDay) -> Unit) {
            instance.onDayUpdateListener = listener
        }

        /**
         * Update marked day.
         * @param instance Instance of the calendar
         * @param markedDay Marked day
         */
        fun onDayUpdate(instance: CalendarData, markedDay: MarkedDay) {
            instance.onDayUpdateListener?.invoke(markedDay)
        }

        /**
         * Update selected date.
         * @param instance Instance of the calendar
         */
        fun updateSelectedDate(instance: CalendarData, selectedDate: SelectedDate) {
            instance.selectedDate = selectedDate
        }

        /**
         * Add new line date to the list.
         * @param instance Instance of the calendar
         * @param lineDate New line date
         */
        fun setLineDate(instance: CalendarData, lineDate: LineDate) {
            instance.lineDate = lineDate
            instance.onLineDateChangeListener?.invoke(lineDate)
        }

        fun setOnLineDateChangeListener(instance: CalendarData, listener: (LineDate) -> Unit) {
            instance.onLineDateChangeListener = listener
        }

    }

}