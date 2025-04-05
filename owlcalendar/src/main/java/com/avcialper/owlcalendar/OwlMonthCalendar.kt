package com.avcialper.owlcalendar

import android.content.Context
import android.widget.LinearLayout.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.owlcalendar.adapter.calendardayname.CalendarDayNameAdapter
import com.avcialper.owlcalendar.adapter.calendarmonth.CalendarMonthAdapter
import com.avcialper.owlcalendar.data.models.Date
import com.avcialper.owlcalendar.data.models.YearAndMonth
import com.avcialper.owlcalendar.data.repositories.DateRepository
import com.avcialper.owlcalendar.databinding.CalendarBinding
import com.avcialper.owlcalendar.helper.CalendarLayoutManager
import com.avcialper.owlcalendar.helper.CalendarManager
import com.avcialper.owlcalendar.helper.CalendarManager.attrs
import com.avcialper.owlcalendar.helper.CalendarManager.locale
import com.avcialper.owlcalendar.util.extensions.getActivity

internal class OwlMonthCalendar(private val binding: CalendarBinding) {

    private val context: Context = binding.root.context
    private val defaultTextColor = binding.tvCalendarHeader.currentTextColor

    // Adapters
    private var calendarDayNameAdapter: CalendarDayNameAdapter? = null
    private var calendarMonthAdapter: CalendarMonthAdapter? = null

    // Custom layout manager for calendar
    private var calendarLayoutManager: CalendarLayoutManager? = null

    /**
     * Initialize calendar view.
     * @param yearAndMonth [YearAndMonth] object. It's use for get days of the month.
     */
    fun init(
        yearAndMonth: YearAndMonth,
        onDayClickListener: (Date) -> Unit
    ) {
        val (year, month) = yearAndMonth

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
        val monthNames = DateRepository.getLocalizedMonthNames()
        val monthName = monthNames[month]
        val label = String.format(locale, "%s %d", monthName, year)

        val dateTextColor = attrs.dateTextColor
        val textColor =
            if (dateTextColor == 0) defaultTextColor else dateTextColor

        binding.tvCalendarHeader.apply {
            text = label
            setTextColor(textColor)
            setOnClickListener {
                val picker = YearMonthPicker(year, month) { y, m ->
                    CalendarManager.setStartDate(y, m)
                }
                context.getActivity()?.let {
                    picker.show(it.supportFragmentManager, "picker")
                }
            }
        }
    }

    /**
     * Initialize adapter for calendar day name. It's name of the days.
     */
    private fun initCalendarDayNameAdapter() {
        val dayNames = DateRepository.getLocalizedDayNames()
        calendarLayoutManager = CalendarLayoutManager(context, RecyclerView.VERTICAL)
        calendarDayNameAdapter = CalendarDayNameAdapter(dayNames)

        binding.rvCalendarDayName.apply {
            itemAnimator = null
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
        onDayClickListener: (Date) -> Unit
    ) {
        val days = DateRepository.getDays(year, month)
        val calendarLayoutManager = CalendarLayoutManager(context, VERTICAL)
        calendarMonthAdapter = CalendarMonthAdapter(days, onDayClickListener)

        binding.rvCalendar.apply {
            setHasFixedSize(true)
            layoutManager = calendarLayoutManager
            adapter = calendarMonthAdapter
        }
    }

}