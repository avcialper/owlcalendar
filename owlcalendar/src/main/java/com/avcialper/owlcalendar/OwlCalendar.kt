package com.avcialper.owlcalendar

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.owlcalendar.adapter.calendar.CalendarAdapter
import com.avcialper.owlcalendar.data.models.CalendarData
import com.avcialper.owlcalendar.data.models.Date
import com.avcialper.owlcalendar.data.models.LineDate
import com.avcialper.owlcalendar.data.models.LineSelectedDate
import com.avcialper.owlcalendar.data.models.MarkedDay
import com.avcialper.owlcalendar.data.repositories.DateRepository
import com.avcialper.owlcalendar.helper.AttrManager
import com.avcialper.owlcalendar.helper.CalendarManager
import com.avcialper.owlcalendar.helper.CalendarScrollListener
import com.avcialper.owlcalendar.helper.CalendarSnapHelper
import com.avcialper.owlcalendar.util.constants.CalendarMode

class OwlCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val calendarData = CalendarData()

    // Custom PagerSnapHelper
    private val snapHelper = CalendarSnapHelper()

    var mode: CalendarMode
        get() = CalendarManager.data.calendarMode
        set(value) {
            CalendarManager.data.calendarMode = value
        }

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.OwlCalendar, defStyleAttr, 0)

        CalendarManager.data = calendarData
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
        val (year, month, _, _) = CalendarManager.data.selectedDate
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
     * Handle click of the day.
     * @param date Clicked day instance
     */
    private fun handleDayClick(date: Date) {
        CalendarManager.onDayClickListener?.invoke(date)
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
    fun setOnLineDateChangeListener(listener: (LineSelectedDate, LineSelectedDate) -> Unit) {
        CalendarManager.onLineDateChangeListener = listener
    }

    /**
     * Set line date.
     * If the calendar is in range selectable mode, scroll to the start of the line date.
     * @param lineDate Line date.
     */
    fun setLineDate(lineDate: LineDate) {
        CalendarManager.setLineDate(lineDate)
        val isRangeSelectableMode = CalendarManager.data.calendarMode.isRangeSelectable()
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
        CalendarManager.restore(calendarData)
    }

    /**
     * Set start date for the calendar.
     * @param year Year of the start date
     * @param month Month of the start date
     */
    fun setStartDate(year: Int, month: Int) {
        CalendarManager.setStartDate(year, month) {
            scrollToPosition(1)
        }
    }
}