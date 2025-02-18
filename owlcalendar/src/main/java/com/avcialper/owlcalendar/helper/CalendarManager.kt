package com.avcialper.owlcalendar.helper

import com.avcialper.owlcalendar.adapter.calendar.CalendarAdapter
import com.avcialper.owlcalendar.data.models.CalendarAttrs
import com.avcialper.owlcalendar.data.models.CalendarData
import com.avcialper.owlcalendar.data.models.Date
import com.avcialper.owlcalendar.data.models.LineDate
import com.avcialper.owlcalendar.data.models.LineSelectedDate
import com.avcialper.owlcalendar.data.models.MarkedDay
import com.avcialper.owlcalendar.data.models.SelectedDate
import com.avcialper.owlcalendar.data.models.YearAndMonth
import com.avcialper.owlcalendar.data.repositories.DateRepository

internal object CalendarManager {
    var data = CalendarData()
    var attrs = CalendarAttrs()
    var calendarAdapter: CalendarAdapter? = null

    var onDayClickListener: ((Date) -> Unit)? = null
    var onLineDateChangeListener: ((LineSelectedDate?, LineSelectedDate?) -> Unit)? = null

    fun addMarkedDays(newDays: List<MarkedDay>) {
        val isSelectableMode = attrs.calendarMode.isSelectable()
        if (isSelectableMode) return
        data.addMarkedDays(newDays)
    }

    fun addMarkedDay(newDay: MarkedDay) {
        val isSelectableMode = attrs.calendarMode.isSelectable()
        if (isSelectableMode) return
        data.addMarkedDay(newDay)
    }

    fun setLineDate(lineDate: LineDate) {
        val isSingleSelectable = attrs.calendarMode.isSingleSelectable()
        if (isSingleSelectable) return

        val isRangeSelectable = attrs.calendarMode.isRangeSelectable()
        if (isRangeSelectable) {
            val (year, moth, dayOfMonth) = lineDate.startDate
            val selectedDate = SelectedDate(year, moth, dayOfMonth, data.calendarPosition)
            data.selectedDate = selectedDate
        }

        data.lineDate = lineDate
    }

    private fun clearLineDate() {
        data.clearLineDate()
    }

    private fun handleRangeSelection(date: SelectedDate) {
        val oldSelectedDate = data.selectedDate
        val isEqual = oldSelectedDate.isEqual(date)

        if (isEqual || data.lineDate != null) {
            clearLineDate()
            onLineDateChangeListener?.invoke(null, null)
            return
        }

        val isBefore = oldSelectedDate.isBefore(date)
        val startDate = if (isBefore) oldSelectedDate else date
        val endDate = if (isBefore) date else oldSelectedDate

        val start = LineSelectedDate(startDate.year, startDate.month, startDate.dayOfMonth)
        val end = LineSelectedDate(endDate.year, endDate.month, endDate.dayOfMonth)

        val lineDate = LineDate(start, end)
        setLineDate(lineDate)
        onLineDateChangeListener?.invoke(start, end)
    }

    fun handleDayClick(date: Date, clickedPosition: Int, onNotifyItemChanged: (Int) -> Unit) {
        val oldPosition = data.selectedDate.calendarPosition

        if (oldPosition != clickedPosition && oldPosition != null)
            onNotifyItemChanged.invoke(oldPosition)
        else
            onNotifyItemChanged.invoke(clickedPosition)


        val selectedDate = SelectedDate(date.year, date.month, date.dayOfMonth, clickedPosition)

        val isRangeSelectable = attrs.calendarMode.isRangeSelectable()
        if (isRangeSelectable) {
            handleRangeSelection(selectedDate)
        }

        data.selectedDate = selectedDate
    }

    fun setOnMarkedDayAddedListener(listener: (MarkedDay) -> Unit) {
        data.setOnMarkedDayAddedListener(listener)
    }

    fun setOnLineDateChangeListener(listener: (LineDate?) -> Unit) {
        data.setOnLineDateChangeListener(listener)
    }

    fun setOnDayUpdateListener(listener: (MarkedDay) -> Unit) {
        data.setOnDayUpdateListener(listener)
    }

    fun onDayUpdate(markedDay: MarkedDay) {
        data.onDayUpdate(markedDay)
    }

    /**
     * Find marked day in the list.
     * @param date Search date
     */
    fun findMarkedDay(date: Date): MarkedDay? = data.findMarkedDay(date)

    /**
     * Updates the calendar state with the provided data.
     * Call this function to restore the calendar data when the calendar manager instance
     * needs to be reinitialized or updated with new data during the app's lifecycle.
     */
    fun restore(instance: CalendarData, attrs: CalendarAttrs) {
        this.data = instance
        this.attrs = attrs
    }

    /**
     * Handles the swipe gesture on the first item in the list.
     * Adds the previous month to the beginning of the calendar adapter.
     */
    private fun handleFirstItemSwipe() {
        calendarAdapter?.let {
            val prevMonth = it.firstItem.prev()
            it.addItemToStart(prevMonth)
            data.calendarPosition++
        }
    }

    /**
     * Handles the swipe gesture on the last item in the list.
     * Adds the next month to the end of the calendar adapter.
     */
    private fun handleLastItemSwipe() {
        calendarAdapter?.let {
            val nextMonth = it.lastItem.next()
            it.addItemToEnd(nextMonth)
        }
    }

    /**
     * Used to process a calendar position change. When the calendar is shifted to the first item,
     * the new item is added to the beginning of the date list. And when the calendar is shifted
     * to the last item, the new item is added at the end of the date list. In this way the calendar
     * data is automatically updated.
     */
    fun onPositionChanged() {
        calendarAdapter?.let {
            val position = data.calendarPosition
            if (position == 0) {
                handleFirstItemSwipe()
                data.calendarPosition = 1
            } else if (position == it.itemCount - 1)
                handleLastItemSwipe()
        }
    }

    /**
     * Set start date for the calendar.
     * @param year Year of the start date
     * @param month Month of the start date
     */
    fun setStartDate(year: Int, month: Int) {
        data.startDate = YearAndMonth(year, month)
        calendarAdapter?.let {
            val dateList = DateRepository.getStartValues(year, month)
            it.updateDateList(dateList)
            data.calendarPosition = 1
            data.onStartDateChangeListener?.invoke()
        }
    }

    /**
     * Set start date for the calendar.
     * @param year Year of the start date
     * @param month Month of the start date
     * @param dayOfMonth Day of the month of the start date
     */
    fun setStartDate(year: Int, month: Int, dayOfMonth: Int) {
        setStartDate(year, month)
        data.selectedDate = SelectedDate(year, month, dayOfMonth, 1)
    }
}