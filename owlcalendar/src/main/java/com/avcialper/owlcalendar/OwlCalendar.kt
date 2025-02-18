package com.avcialper.owlcalendar

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.owlcalendar.adapter.calendar.CalendarAdapter
import com.avcialper.owlcalendar.data.models.CalendarAttrs
import com.avcialper.owlcalendar.data.models.CalendarData
import com.avcialper.owlcalendar.data.models.Date
import com.avcialper.owlcalendar.data.models.LineDate
import com.avcialper.owlcalendar.data.models.LineSelectedDate
import com.avcialper.owlcalendar.data.models.MarkedDay
import com.avcialper.owlcalendar.data.repositories.DateRepository
import com.avcialper.owlcalendar.helper.AttrManager
import com.avcialper.owlcalendar.helper.CalendarManager
import com.avcialper.owlcalendar.helper.CalendarManager.attrs
import com.avcialper.owlcalendar.helper.CalendarManager.data
import com.avcialper.owlcalendar.helper.CalendarScrollListener
import com.avcialper.owlcalendar.helper.CalendarSnapHelper

class OwlCalendar @JvmOverloads constructor(
    context: Context,
    defAttrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, defAttrs, defStyleAttr) {

    private val calendarData = CalendarData()
    private val calendarAttrs = CalendarAttrs()

    // Custom PagerSnapHelper
    private val snapHelper = CalendarSnapHelper()

    var calendarMode = attrs.calendarMode
        set(value) {
            field = value
            attrs.calendarMode = value
        }

    var pickerButtonText = attrs.pickerButtonText
        set(value) {
            field = value
            attrs.pickerButtonText = value
        }

    val startYear
        get() = data.startDate.year

    val startMonth
        get() = data.startDate.month

    init {
        CalendarManager.apply {
            data = calendarData
            attrs = calendarAttrs

            data.onStartDateChangeListener = { scrollToPosition(1) }
        }

        val typedArray =
            context.obtainStyledAttributes(defAttrs, R.styleable.OwlCalendar, defStyleAttr, 0)
        AttrManager(typedArray, context) {
            typedArray.recycle()
        }

        initAdapter()
        CalendarManager.calendarAdapter = adapter as CalendarAdapter
    }

    /**
     * Initialize adapter.
     */
    private fun initAdapter() {
        val (year, month, _, _) = data.selectedDate
        val dateList = DateRepository.getStartValues(year, month)
        itemAnimator = null

        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        adapter = CalendarAdapter(dateList)

        snapHelper.attachToRecyclerView(this)

        val calendarScrollListener = CalendarScrollListener()
        addOnScrollListener(calendarScrollListener)

        scrollToPosition(1)
    }

    /**
     * Add new marked days to the list.
     * This function is only for normal mode.
     * @param newDays New marked days
     */
    fun addMarkedDays(newDays: List<MarkedDay>) {
        CalendarManager.addMarkedDays(newDays)
    }

    /**
     * Add new marked day to the list.
     * This function is only for normal mode.
     * @param newDay New marked day
     */
    fun addMarkedDay(newDay: MarkedDay) {
        CalendarManager.addMarkedDay(newDay)
    }

    /**
     * To handle click of the day.
     * @param listener Click listener.
     */
    fun setOnDayClickListener(listener: (Date) -> Unit) {
        CalendarManager.onDayClickListener = listener
    }

    /**
     * Set listener for day update.
     * @param listener Listener
     */
    fun setOnLineDateChangeListener(listener: (LineSelectedDate?, LineSelectedDate?) -> Unit) {
        CalendarManager.onLineDateChangeListener = listener
    }

    /**
     * Set line date.
     * If the calendar is in range selectable mode, scroll to the start of the line date.
     * @param lineDate Line date.
     */
    fun setLineDate(lineDate: LineDate) {
        CalendarManager.setLineDate(lineDate)
        val isRangeSelectableMode = attrs.calendarMode.isRangeSelectable()
        if (isRangeSelectableMode) {
            val (year, month, _) = lineDate.startDate
            setStartDate(year, month)
        }
    }

    /**
     * Updates the calendar state with the provided data.
     * Call this function to restore the calendar data when the calendar manager instance
     * needs to be reinitialized or updated with new data during the app's lifecycle.
     */
    fun restore() {
        CalendarManager.restore(calendarData, calendarAttrs)
    }

    /**
     * Set start date for the calendar.
     * @param year Year of the start date
     * @param month Month of the start date. Is between 0 and 11.
     */
    fun setStartDate(year: Int, month: Int) {
        CalendarManager.setStartDate(year, month)
    }

    /**
     * Set start date for the calendar. If the calendar is in single selectable mode, use this
     * function to change the start date of the calendar.
     * @param year Year of the start date
     * @param month Month of the start date. Is between 0 and 11.
     * @param dayOfMonth Day of the month of the start date
     */
    fun setStartDate(year: Int, month: Int, dayOfMonth: Int) {
        val isSingleSelectable = attrs.calendarMode.isSingleSelectable()
        if (isSingleSelectable)
            CalendarManager.setStartDate(year, month, dayOfMonth)
    }
}