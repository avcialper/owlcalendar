package com.avcialper.owlcalendar.adapter.calendar

import androidx.recyclerview.widget.RecyclerView
import com.avcialper.jdatetime.model.JDayOfMonth
import com.avcialper.owlcalendar.OwlMonthCalendar
import com.avcialper.owlcalendar.data.models.OwlDate
import com.avcialper.owlcalendar.databinding.CalendarViewBinding

internal class CalendarViewHolder(binding: CalendarViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    // OwlCalendarView instance. It's a custom calendar view.
    private val owlMonthCalendar = OwlMonthCalendar(binding)

    fun bind(
        owlDate: OwlDate,
        onDayClickListener: (JDayOfMonth) -> Unit
    ) {
        owlMonthCalendar.init(owlDate, onDayClickListener)
    }
}