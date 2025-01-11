package com.avcialper.owlcalendar.adapter.calendarview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.jdatetime.JDateTime
import com.avcialper.jdatetime.model.JDayOfMonth
import com.avcialper.owlcalendar.databinding.CalendarDayBinding

class CalendarViewAdapter(
    private val days: List<JDayOfMonth?>,
    private val onDayClickListener: (JDayOfMonth) -> Unit
) : RecyclerView.Adapter<CalendarViewViewHolder>() {

    private var selectedDate: String = JDateTime.instance.date

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = CalendarDayBinding.inflate(layoutInflater, parent, false)
        return CalendarViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewViewHolder, position: Int) {
        val day = days[position]
        val isSelected = selectedDate == day?.date

        if (day == null)
            holder.bind(null)
        else {
            holder.bind(day, isSelected) { jDayOfMonth ->
                val date = jDayOfMonth.date
                handleSelectedDate(date)
                onDayClickListener.invoke(jDayOfMonth)
            }
        }
    }

    override fun getItemCount(): Int = days.size

    /**
     * Handle selected date on the calendar.
     */
    private fun handleSelectedDate(date: String) {
        val oldDatePosition = days.indexOfFirst { it?.date == selectedDate }
        val newDatePosition = days.indexOfFirst { it?.date == date }

        notifyItemChanged(oldDatePosition)
        notifyItemChanged(newDatePosition)

        selectedDate = date
    }
}