package com.avcialper.owlcalendar

import android.content.Context
import android.content.res.TypedArray
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

class OwlCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var typedArray: TypedArray =
        context.obtainStyledAttributes(attrs, R.styleable.OwlCalendar, defStyleAttr, 0)

    private val calendarData = CalendarData()

    // Custom PagerSnapHelper
    private val snapHelper = CalendarSnapHelper()

    init {
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
        val dateList = DateRepository().getStartValues(year, month)
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

    fun setLineDate(lineDate: LineDate) {
        CalendarManager.setLineDate(lineDate)
    }

    /**
     * Updates the calendar state with the provided data.
     * Call this function to restore the calendar data when the calendar manager instance
     * needs to be reinitialized or updated with new data during the app's lifecycle.
     */
    fun restore() {
        CalendarManager.restore(calendarData)
    }
}