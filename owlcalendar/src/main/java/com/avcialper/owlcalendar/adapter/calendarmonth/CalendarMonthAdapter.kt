package com.avcialper.owlcalendar.adapter.calendarmonth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.jdatetime.model.JDayOfMonth
import com.avcialper.owlcalendar.data.models.MarkedDay
import com.avcialper.owlcalendar.databinding.CalendarDayBinding
import com.avcialper.owlcalendar.util.constants.OwlCalendarValues
import com.avcialper.owlcalendar.util.constants.OwlCalendarValues.selectedDate

internal class CalendarMonthAdapter(
    private val days: List<JDayOfMonth?>,
    private val onDayClickListener: (JDayOfMonth) -> Unit
) : RecyclerView.Adapter<CalendarMonthViewHolder>() {

    init {
        OwlCalendarValues.setOnMarkedDayAddedListener(::onMarkedDayAddedListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarMonthViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = CalendarDayBinding.inflate(layoutInflater, parent, false)
        return CalendarMonthViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarMonthViewHolder, position: Int) {
        val day = days[position]
        val selectedDate = OwlCalendarValues.selectedDate.date
        val isSelected = selectedDate == day?.date

        if (day != null) {
            holder.bind(day, isSelected) { jDayOfMonth ->
                val date = jDayOfMonth.date
                handleDayClick(date)
                onDayClickListener.invoke(jDayOfMonth)
            }
        }
    }

    override fun getItemCount(): Int = days.size

    /**
     * Handle selected date on the calendar.
     */
    private fun handleDayClick(date: String) {
        val oldDatePosition = days.indexOfFirst { it?.date == selectedDate.date }
        val newDatePosition = days.indexOfFirst { it?.date == date }

        if (oldDatePosition != newDatePosition) {
            notifyItemChanged(oldDatePosition)
            notifyItemChanged(newDatePosition)
        }
    }

    private fun onMarkedDayAddedListener(markedDay: MarkedDay) {
        val markedItem = days.find { it?.date == markedDay.date }
        if (markedItem != null) {
            val index = days.indexOf(markedItem)
            notifyItemChanged(index)
        }
    }

}