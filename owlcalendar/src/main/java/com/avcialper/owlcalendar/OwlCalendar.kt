package com.avcialper.owlcalendar

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.jdatetime.JDateTime
import com.avcialper.owlcalendar.adapter.calendar.CalendarAdapter
import com.avcialper.owlcalendar.data.models.OwlDate
import com.avcialper.owlcalendar.data.models.SelectedDate
import com.avcialper.owlcalendar.helper.CalendarScrollListener
import com.avcialper.owlcalendar.helper.CalendarSnapHelper
import com.avcialper.owlcalendar.util.constants.OwlCalendarValues.selectedDate

class OwlCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    // Custom PagerSnapHelper
    private val snapHelper = CalendarSnapHelper()

    init {
        initAdapter()
    }

    /**
     * Initialize adapter.
     */
    private fun initAdapter() {
        val dateList = initDateList()

        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        adapter = CalendarAdapter(dateList, ::onDayClickListener)

        snapHelper.attachToRecyclerView(this)

        val calendarScrollListener = CalendarScrollListener(snapHelper, ::onPositionChanged)
        addOnScrollListener(calendarScrollListener)

        scrollToPosition(1)
    }

    /**
     * Initializes a date list containing the dates for yesterday, today, and tomorrow.
     *
     * The current date is determined using the `JDateTime` instance. Based on this:
     * - `yesterday` is calculated as the previous date from today.
     * - `today` represents the current date (year and month).
     * - `tomorrow` is calculated as the next date from today.
     *
     * @return A list of dates in the order: yesterday, today, tomorrow.
     */
    private fun initDateList(): List<OwlDate> {
        val jDateTime = JDateTime.instance
        val currentYear = jDateTime.year
        val currentMonth = jDateTime.month

        val today = OwlDate(currentYear, currentMonth)
        val yesterday = today.prev()
        val tomorrow = today.next()

        return listOf(yesterday, today, tomorrow)
    }

    /**
     * To handle click of the day.
     * @param selectedDate Clicked day instance.
     */
    private fun onDayClickListener(selectedDate: SelectedDate) {
//        context.pushToastMessage(selectedDate.date)
    }

    /**
     * Used to process a calendar position change. When the calendar is shifted to the first item,
     * the new item is added to the beginning of the date list. And when the calendar is shifted
     * to the last item, the new item is added at the end of the date list. In this way the calendar
     * data is automatically updated.
     * @param position Current position of the calendar.
     */
    private fun onPositionChanged(position: Int) {
        adapter?.let {
            val adapter = it as CalendarAdapter
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
        selectedDate.increasePosition()
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
}