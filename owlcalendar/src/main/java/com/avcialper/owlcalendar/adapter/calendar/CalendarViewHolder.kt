package com.avcialper.owlcalendar.adapter.calendar

import androidx.recyclerview.widget.RecyclerView
import com.avcialper.jdatetime.model.JDate
import com.avcialper.owlcalendar.OwlMonthCalendar
import com.avcialper.owlcalendar.data.models.CalendarData
import com.avcialper.owlcalendar.data.models.MonthAndYear
import com.avcialper.owlcalendar.databinding.CalendarBinding

internal class CalendarViewHolder(binding: CalendarBinding) :
    RecyclerView.ViewHolder(binding.root) {

    // OwlCalendarView instance. It's a custom calendar view.
    private val owlMonthCalendar = OwlMonthCalendar(binding)

    fun bind(
        monthAndYear: MonthAndYear,
        calendarData: CalendarData,
        onDayClickListener: (JDate) -> Unit
    ) {
        owlMonthCalendar.init(monthAndYear, calendarData, onDayClickListener)
    }
}