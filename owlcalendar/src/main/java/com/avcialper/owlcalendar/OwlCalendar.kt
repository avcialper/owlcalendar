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
import com.avcialper.owlcalendar.data.models.SelectedDate
import com.avcialper.owlcalendar.data.repositories.DateRepository
import com.avcialper.owlcalendar.helper.CalendarScrollListener
import com.avcialper.owlcalendar.helper.CalendarSnapHelper
import com.avcialper.owlcalendar.util.constants.CalendarMode

class OwlCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var typedArray: TypedArray =
        context.obtainStyledAttributes(attrs, R.styleable.OwlCalendar, defStyleAttr, 0)

    private val calendarData = CalendarData()

    // Custom PagerSnapHelper
    private val snapHelper =
        CalendarSnapHelper { position -> calendarData.calendarPosition = position }

    private var onDayClickListener: ((Date) -> Unit) = { _ -> }
    private var onLineDateChangeListener: ((LineSelectedDate, LineSelectedDate) -> Unit) =
        { _, _ -> }

    init {
        val defBackgroundColor = getColor(R.color.orange_transparent)
        val defTodayTextColor = getColor(R.color.orange)
        val defTextColor = 0
        val defCalendarMode = CalendarMode.NORMAL

        var selectedBackgroundColor = getAttrColor(
            R.styleable.OwlCalendar_selected_date_background_color,
            defBackgroundColor
        )
        val todayTextColor = getAttrColor(
            R.styleable.OwlCalendar_today_text_color,
            defTodayTextColor
        )
        val dayTextColor = getAttrColor(
            R.styleable.OwlCalendar_day_text_color,
            defTextColor
        )
        val dayNameTextColor = getAttrColor(
            R.styleable.OwlCalendar_day_name_text_color,
            defTextColor
        )
        val dateTextColor = getAttrColor(
            R.styleable.OwlCalendar_date_text_color,
            defTextColor
        )
        val lineBackgroundColor = getAttrColor(
            R.styleable.OwlCalendar_line_background_color,
            defBackgroundColor
        )
        val calendarModeValue = getAttrInt(R.styleable.OwlCalendar_mode, defCalendarMode.value)
        val calendarMode = CalendarMode.fromValue(calendarModeValue)

        if (CalendarMode.isRangeSelectable(calendarMode) && lineBackgroundColor != selectedBackgroundColor)
            selectedBackgroundColor = lineBackgroundColor


        typedArray.recycle()

        calendarData.setAttrs(
            selectedBackgroundColor,
            todayTextColor,
            dayTextColor,
            dayNameTextColor,
            dateTextColor,
            lineBackgroundColor,
            calendarMode
        )

        initAdapter()
    }

    private fun getAttrColor(id: Int, defVal: Int): Int = typedArray.getColor(id, defVal)

    private fun getAttrInt(id: Int, defVal: Int): Int = typedArray.getInt(id, defVal)

    private fun getColor(id: Int): Int = context.getColor(id)

    /**
     * Initialize adapter.
     */
    private fun initAdapter() {
        val dateList = DateRepository().getStartValues()
        itemAnimator = null

        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        adapter =
            CalendarAdapter(dateList, calendarData, ::handleDayClick, ::handleRangeSelection)

        snapHelper.attachToRecyclerView(this)

        val calendarScrollListener = CalendarScrollListener(::onPositionChanged)
        addOnScrollListener(calendarScrollListener)

        scrollToPosition(1)
    }

    /**
     * Used to process a calendar position change. When the calendar is shifted to the first item,
     * the new item is added to the beginning of the date list. And when the calendar is shifted
     * to the last item, the new item is added at the end of the date list. In this way the calendar
     * data is automatically updated.
     */
    private fun onPositionChanged() {
        adapter?.let {
            val adapter = it as CalendarAdapter
            val position = calendarData.calendarPosition
            if (position == 0) {
                handleFirstItemSwipe()
                snapHelper.addedNewItemOnStart()
            } else if (position == adapter.itemCount - 1)
                handleLastItemSwipe()
        }
    }

    /**
     * Handles the swipe gesture on the first item in the list.
     * Adds the previous month to the beginning of the calendar adapter.
     */
    private fun handleFirstItemSwipe() {
        val adapter = adapter as CalendarAdapter
        val prevMonth = adapter.firstItem.prev()
        adapter.addItemToStart(prevMonth)
        calendarData.selectedDate.increasePosition()
    }

    /**
     * Handles the swipe gesture on the last item in the list.
     * Adds the next month to the end of the calendar adapter.
     */
    private fun handleLastItemSwipe() {
        val adapter = adapter as CalendarAdapter
        val nextMonth = adapter.lastItem.next()
        adapter.addItemToEnd(nextMonth)
    }

    /**
     * Add new marked days to the list.
     * This function is only for normal mode.
     * @param newDays New marked days
     */
    fun addMarkedDays(newDays: List<MarkedDay>) {
        val isSelectableType = CalendarMode.isSelectable(calendarData.calendarMode)
        if (isSelectableType) return
        CalendarData.addMarkedDays(calendarData, newDays)
    }

    /**
     * Add new marked day to the list.
     * This function is only for normal mode.
     * @param newDay New marked day
     */
    fun addMarkedDay(newDay: MarkedDay) {
        val isSelectableType = CalendarMode.isSelectable(calendarData.calendarMode)
        if (isSelectableType) return
        CalendarData.addMarkedDay(calendarData, newDay)
    }

    /**
     * Add new line date to the list.
     * @param lineDate New line date
     */
    fun setLineDate(lineDate: LineDate) {
        val isSingleSelectable = CalendarMode.isSingleSelectable(calendarData.calendarMode)
        if (isSingleSelectable) return

        val isRangeSelectable = CalendarMode.isRangeSelectable(calendarData.calendarMode)
        if (isRangeSelectable) {
            val (year, month, dayOfMonth) = lineDate.startDate
            val selectedDate = SelectedDate(year, month, dayOfMonth, calendarData.calendarPosition)
            calendarData.selectedDate = selectedDate
        }

        CalendarData.setLineDate(calendarData, lineDate)
    }

    /**
     * Handle click of the day.
     * @param date Clicked day instancenot
     */
    private fun handleDayClick(date: Date) {
        onDayClickListener.invoke(date)
    }

    /**
     * Handle range selection.
     * @param date Selected date
     */
    private fun handleRangeSelection(date: SelectedDate) {
        val oldSelectedDate = calendarData.selectedDate
        val isEqual = oldSelectedDate.isEqual(date)

        if (isEqual || calendarData.lineDate != null) {
            CalendarData.clearLineDate(calendarData)
            return
        }

        val isBefore = oldSelectedDate.isBefore(date)
        val startDate = if (isBefore) oldSelectedDate else date
        val endDate = if (isBefore) date else oldSelectedDate

        val start = LineSelectedDate(
            startDate.year,
            startDate.month,
            startDate.dayOfMonth
        )
        val end = LineSelectedDate(
            endDate.year,
            endDate.month,
            endDate.dayOfMonth
        )
        val lineDate = LineDate(start, end)
        setLineDate(lineDate)
        onLineDateChangeListener.invoke(start, end)
    }

    /**
     * To handle click of the day.
     * @param listener Click listener.
     */
    fun setOnDayClickListener(listener: (Date) -> Unit) {
        onDayClickListener = listener
    }

    /**
     * Set listener for day update.
     * @param listener Listener
     */
    fun setOnLineDateChangeListener(listener: (LineSelectedDate, LineSelectedDate) -> Unit) {
        onLineDateChangeListener = listener
    }
}