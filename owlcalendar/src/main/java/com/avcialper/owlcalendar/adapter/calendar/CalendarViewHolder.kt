package com.avcialper.owlcalendar.adapter.calendar

import androidx.recyclerview.widget.RecyclerView
import com.avcialper.jdatetime.model.JDayOfMonth
import com.avcialper.owlcalendar.OwlMonthCalendar
import com.avcialper.owlcalendar.data.models.MonthAndYear
import com.avcialper.owlcalendar.databinding.CalendarBinding

internal class CalendarViewHolder(binding: CalendarBinding) :
    RecyclerView.ViewHolder(binding.root) {

    // OwlCalendarView instance. It's a custom calendar view.
    private val owlMonthCalendar = OwlMonthCalendar(binding)

    fun bind(
        monthAndYear: MonthAndYear,
        onDayClickListener: (JDayOfMonth) -> Unit
    ) {
        owlMonthCalendar.init(monthAndYear, onDayClickListener)
    }
}