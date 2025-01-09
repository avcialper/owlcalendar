package com.avcialper.owlcalendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.avcialper.jdatetime.JDateTime
import com.avcialper.jdatetime.model.JDayOfMonth
import com.avcialper.owlcalendar.adapter.calendar.CalendarAdapter
import com.avcialper.owlcalendar.adapter.calendardayname.CalendarDayNameAdapter
import com.avcialper.owlcalendar.databinding.CalendarBinding
import com.avcialper.owlcalendar.manager.CalendarLayoutManager
import com.avcialper.owlcalendar.util.constants.Constants
import com.avcialper.owlcalendar.util.extensions.adjustStart
import com.avcialper.owlcalendar.util.extensions.pushToastMessage
import java.util.Locale

class OwlCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: CalendarBinding
    private val jDateTime = JDateTime.instance

    private var inputYear = jDateTime.year
    private var inputMonth = jDateTime.month

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = CalendarBinding.inflate(layoutInflater, this, true)

        setHeader()
        initCalendarDayNameAdapter()
        initCalendarAdapter()
    }

    private fun initCalendarDayNameAdapter() {
        val dayNames = Constants.dayNames
        val calendarLayoutManager = CalendarLayoutManager(context, VERTICAL)

        binding.rvCalendarDayName.apply {
            adapter = CalendarDayNameAdapter(dayNames)
            layoutManager = calendarLayoutManager
        }
    }

    private fun initCalendarAdapter() {
        val days = getDate()
        val calendarLayoutManager = CalendarLayoutManager(context, VERTICAL)

        binding.rvCalendar.apply {
            layoutManager = calendarLayoutManager
            adapter = CalendarAdapter(days, ::onDayClickListener)
        }
    }

    private fun getDate(): List<JDayOfMonth?> {
        val year = inputYear
        val month = inputMonth
        val daysInMonth = jDateTime.getAllDaysOfMonth(year, month).adjustStart()
        return daysInMonth
    }

    private fun setHeader() {
        val year = inputYear
        val month = inputMonth

        val monthName = Constants.monthNames[month]
        val locale = Locale.getDefault()
        binding.tvCalendarHeader.text = String.format(locale, "%s %d", monthName, year)
    }

    /**
     * Sets the year of the calendar.
     * @param year The year to set.
     */
    fun setYear(year: Int) {
        inputYear = year
        setHeader()
        initCalendarAdapter()
    }

    /**
     * Sets the month of the calendar.
     * @param month The month to set. The month should be between 0 and 11.
     */
    fun setMonth(month: Int) {
        inputMonth = month
        setHeader()
        initCalendarAdapter()
    }

    private fun onDayClickListener(day: JDayOfMonth) {
        context.pushToastMessage(day.date)
    }

}