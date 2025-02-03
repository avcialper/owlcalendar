package com.avcialper.owlcalendar.data.models

import com.avcialper.jdatetime.JDateTime
import com.avcialper.jdatetime.model.JDayOfMonth

internal class CalendarData {

    private val jDateTime = JDateTime.instance
    var selectedDate = SelectedDate(jDateTime.year, jDateTime.month, jDateTime.dayOfMonth, 1)

    private val markedDays = mutableListOf<MarkedDay>()
    private var onMarkedDayAddedListener: ((MarkedDay) -> Unit)? = null
    private var onDayUpdateListener: ((MarkedDay) -> Unit)? = null

    var calendarPosition = 1

    companion object {

        /**
         * Add new marked days to the list.
         * @param newDays New marked days
         */
        fun addMarkedDays(instance: CalendarData, newDays: List<MarkedDay>) {
            newDays.forEach { date ->
                addMarkedDay(instance, date)
            }
        }

        /**
         * Add new marked day to the list.
         * @param date New marked day
         */
        fun addMarkedDay(instance: CalendarData, date: MarkedDay) {
            val existingDay = findMarkedDay(instance, date)
            if (existingDay != null) {
                existingDay.color = date.color
            } else {
                instance.markedDays.add(date)
            }
            instance.onMarkedDayAddedListener?.invoke(date)
        }

        /**
         * Find marked day by date.
         * @param date Date of the marked day
         */
        fun findMarkedDay(instance: CalendarData, date: MarkedDay): MarkedDay? =
            instance.markedDays.findMarkedDay(date)

        /**
         * Find marked day by date.
         * @param jDayOfMonth Date of the marked day
         */
        fun findMarkedDay(instance: CalendarData, jDayOfMonth: JDayOfMonth): MarkedDay? =
            instance.markedDays.findMarkedDay(jDayOfMonth)

        /**
         * Set listener for new marked day.
         */
        fun setOnMarkedDayAddedListener(instance: CalendarData, listener: (MarkedDay) -> Unit) {
            instance.onMarkedDayAddedListener = listener
        }

        /**
         * Set listener for day update.
         */
        fun setOnDayUpdateListener(instance: CalendarData, listener: (MarkedDay) -> Unit) {
            instance.onDayUpdateListener = listener
        }

        /**
         * Update marked day.
         * @param markedDay Marked day
         */
        fun onDayUpdate(instance: CalendarData, markedDay: MarkedDay) {
            instance.onDayUpdateListener?.invoke(markedDay)
        }

        /**
         * Update selected date.
         */
        fun updateSelectedDate(instance: CalendarData, selectedDate: SelectedDate) {
            instance.selectedDate = selectedDate
        }

    }

}