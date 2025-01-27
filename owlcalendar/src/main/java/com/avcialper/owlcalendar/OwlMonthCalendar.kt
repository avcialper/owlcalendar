package com.avcialper.owlcalendar

import android.content.Context
import android.widget.LinearLayout.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.jdatetime.JDateTime
import com.avcialper.jdatetime.model.JDayOfMonth
import com.avcialper.owlcalendar.adapter.calendardayname.CalendarDayNameAdapter
import com.avcialper.owlcalendar.adapter.calendarmonth.CalendarMonthAdapter
import com.avcialper.owlcalendar.data.models.OwlDate
import com.avcialper.owlcalendar.databinding.CalendarBinding
import com.avcialper.owlcalendar.helper.CalendarLayoutManager
import com.avcialper.owlcalendar.util.constants.Constants
import com.avcialper.owlcalendar.util.extensions.adjustDay
import java.util.Locale

internal class OwlMonthCalendar(private val binding: CalendarBinding) {

    private val context: Context = binding.root.context
    private val jDateTime = JDateTime.instance

    // Adapters
    private var calendarDayNameAdapter: CalendarDayNameAdapter? = null
    private var calendarMonthAdapter: CalendarMonthAdapter? = null

    // Custom layout manager for calendar
    private var calendarLayoutManager: CalendarLayoutManager? = null

    /**
     * Initialize calendar view.
     * @param owlDate [OwlDate] object. It's use for get days of the month.
     */
    fun init(
        owlDate: OwlDate,
        onDayClickListener: (JDayOfMonth) -> Unit
    ) {
        val year = owlDate.year
        val month = owlDate.month

        initHeader(year, month)
        initCalendarDayNameAdapter()
        initCalendarAdapter(year, month, onDayClickListener)
    }

    /**
     * Initialize header of the calendar.
     * @param year Year of the calendar
     * @param month Month of the calendar
     */
    private fun initHeader(year: Int, month: Int) {
        val monthName = Constants.monthNames[month]
        val locale = Locale.getDefault()
        binding.tvCalendarHeader.text = String.format(locale, "%s %d", monthName, year)
    }

    /**
     * Initialize adapter for calendar day name. It's name of the days.
     */
    private fun initCalendarDayNameAdapter() {
        val dayNames = Constants.dayNames
        calendarLayoutManager = CalendarLayoutManager(context, RecyclerView.VERTICAL)
        calendarDayNameAdapter = CalendarDayNameAdapter(dayNames)

        binding.rvCalendarDayName.apply {
            setHasFixedSize(true)
            adapter = calendarDayNameAdapter
            layoutManager = calendarLayoutManager
        }
    }

    /**
     * Initialize adapter for calendar view.
     * @param year Year of the calendar
     * @param month Month of the calendar
     * @param onDayClickListener Click listener for the day
     */
    private fun initCalendarAdapter(
        year: Int,
        month: Int,
        onDayClickListener: (JDayOfMonth) -> Unit
    ) {
        val days = getDays(year, month)
        val calendarLayoutManager = CalendarLayoutManager(context, VERTICAL)
        calendarMonthAdapter = CalendarMonthAdapter(days, onDayClickListener)

        binding.rvCalendar.apply {
            setHasFixedSize(true)
            layoutManager = calendarLayoutManager
            adapter = calendarMonthAdapter
        }
    }

    /**
     * Get days of the month.
     * @param year Year of the calendar
     * @param month Month of the calendar
     * @return List of [JDayOfMonth] objects.
     */
    private fun getDays(year: Int, month: Int): List<JDayOfMonth?> {
        val daysInMonth = jDateTime.getAllDaysOfMonth(year, month).adjustDay()
        return daysInMonth
    }
}