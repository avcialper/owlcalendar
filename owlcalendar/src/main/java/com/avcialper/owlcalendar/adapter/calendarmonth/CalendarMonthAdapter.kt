package com.avcialper.owlcalendar.adapter.calendarmonth

import android.view.LayoutInflater
import android.view.ViewGroup
import com.avcialper.jdatetime.model.JDayOfMonth
import com.avcialper.owlcalendar.adapter.BaseAdapter
import com.avcialper.owlcalendar.data.models.CalendarData
import com.avcialper.owlcalendar.data.models.MarkedDay
import com.avcialper.owlcalendar.databinding.CalendarDayBinding
import com.avcialper.owlcalendar.util.extensions.findIndex

internal class CalendarMonthAdapter(
    private val days: List<JDayOfMonth?>,
    private val calendarData: CalendarData,
    private val onDayClickListener: (JDayOfMonth) -> Unit
) : BaseAdapter<CalendarMonthViewHolder>() {

    init {
        CalendarData.setOnDayUpdateListener(calendarData, ::onDayUpdateListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarMonthViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = CalendarDayBinding.inflate(layoutInflater, parent, false)
        return CalendarMonthViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarMonthViewHolder, position: Int) {
        val day = days[position]
        val isSelected = calendarData.selectedDate.isEqual(day)

        if (day != null) {
            holder.bind(day, isSelected, calendarData) { jDayOfMonth ->
                handleDayClick(jDayOfMonth)
                onDayClickListener.invoke(jDayOfMonth)
            }
        }
    }

    override fun getItemCount(): Int = days.size

    /**
     * Handle click of the day.
     * @param jDayOfMonth Clicked day instance
     */
    override fun handleDayClick(jDayOfMonth: JDayOfMonth) {
        val selectedDate = calendarData.selectedDate
        val oldPosition = days.findIndex(selectedDate)
        val newPosition = days.findIndex(jDayOfMonth)

        if (oldPosition != newPosition) {
            notifyItemChanged(oldPosition)
            notifyItemChanged(newPosition)
        }
    }

    private fun onDayUpdateListener(markedDay: MarkedDay) {
        val position = days.findIndex(markedDay)
        notifyItemChanged(position)
    }
}