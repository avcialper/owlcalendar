package com.avcialper.owlcalendar.adapter.calendarmonth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.owlcalendar.data.models.Date
import com.avcialper.owlcalendar.data.models.MarkedDay
import com.avcialper.owlcalendar.data.models.findIndex
import com.avcialper.owlcalendar.databinding.CalendarDayBinding
import com.avcialper.owlcalendar.helper.CalendarManager
import com.avcialper.owlcalendar.helper.CalendarManager.data

internal class CalendarMonthAdapter(
    private val days: List<Date?>,
    private val onDayClickListener: (Date) -> Unit
) : RecyclerView.Adapter<CalendarMonthViewHolder>() {

    init {
        CalendarManager.setOnDayUpdateListener(::onDayUpdateListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarMonthViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = CalendarDayBinding.inflate(layoutInflater, parent, false)
        return CalendarMonthViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarMonthViewHolder, position: Int) {
        val day = days[position]

        if (day != null) {
            holder.bind(day) { date ->
                handleDayClick(date)
                onDayClickListener.invoke(date)
            }
        }
    }

    override fun getItemCount(): Int = days.size

    /**
     * Handle click of the day.
     * @param date Clicked day instance
     */
    private fun handleDayClick(date: Date) {
        val selectedDate = data.selectedDate
        val oldPosition = days.findIndex(selectedDate)
        val newPosition = days.findIndex(date)

        if (oldPosition != newPosition) {
            notifyItemChanged(oldPosition)
            notifyItemChanged(newPosition)
        }
    }

    /**
     * Update the marked days.
     * @param markedDay Updated marked day
     */
    private fun onDayUpdateListener(markedDay: MarkedDay) {
        val position = days.findIndex(markedDay)
        notifyItemChanged(position)
    }
}