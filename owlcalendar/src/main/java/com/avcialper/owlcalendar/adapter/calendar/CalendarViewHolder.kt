package com.avcialper.owlcalendar.adapter.calendar

import androidx.recyclerview.widget.RecyclerView
import com.avcialper.jdatetime.model.JDayOfMonth
import com.avcialper.owlcalendar.OwlCalendarView
import com.avcialper.owlcalendar.data.models.OwlDate
import com.avcialper.owlcalendar.databinding.CalendarViewBinding

class CalendarViewHolder(binding: CalendarViewBinding) : RecyclerView.ViewHolder(binding.root) {

    // OwlCalendarView instance. It's a custom calendar view.
    private val owlCalendarView = OwlCalendarView(binding)

    fun bind(owlDate: OwlDate, onDayClickListener: (JDayOfMonth) -> Unit) {
        owlCalendarView.init(owlDate, onDayClickListener)
    }
}