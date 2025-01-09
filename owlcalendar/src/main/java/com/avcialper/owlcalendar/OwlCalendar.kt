package com.avcialper.owlcalendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.avcialper.jdatetime.JDateTime
import com.avcialper.jdatetime.model.JDayOfMonth
import com.avcialper.owlcalendar.adapter.calendar.CalendarAdapter
import com.avcialper.owlcalendar.databinding.CalendarBinding
import com.avcialper.owlcalendar.manager.CalendarLayoutManager
import com.avcialper.owlcalendar.util.extensions.adjustStart
import com.avcialper.owlcalendar.util.extensions.pushToastMessage

class OwlCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var calendarAdapter: CalendarAdapter
    private val binding: CalendarBinding
    private val jDateTime = JDateTime.instance

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = CalendarBinding.inflate(layoutInflater, this, true)

        initCalendarAdapter()
    }

    private fun initCalendarAdapter() {
        val days = getDate()
        val calendarLayoutManager = CalendarLayoutManager(context)

        calendarAdapter = CalendarAdapter(days, ::onDayClickListener)
        binding.rvCalendar.apply {
            layoutManager = calendarLayoutManager
            adapter = calendarAdapter
        }
    }

    private fun getDate(): List<JDayOfMonth?> {
        val year = jDateTime.year
        val month = jDateTime.month
        val daysInMonth = jDateTime.getAllDaysOfMonth(year, month).adjustStart()
        return daysInMonth
    }

    private fun onDayClickListener(day: JDayOfMonth) {
        context.pushToastMessage(day.date)
    }

}