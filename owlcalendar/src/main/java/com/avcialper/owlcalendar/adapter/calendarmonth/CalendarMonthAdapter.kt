package com.avcialper.owlcalendar.adapter.calendarmonth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.jdatetime.model.JDayOfMonth
import com.avcialper.owlcalendar.data.models.SelectedDate
import com.avcialper.owlcalendar.databinding.CalendarDayBinding

class CalendarMonthAdapter(
    private val days: List<JDayOfMonth?>,
    private val onDayClickListener: (JDayOfMonth) -> Unit
) : RecyclerView.Adapter<CalendarMonthViewHolder>() {

    private var handleSelectedDate: (() -> SelectedDate)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarMonthViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = CalendarDayBinding.inflate(layoutInflater, parent, false)
        return CalendarMonthViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarMonthViewHolder, position: Int) {
        val day = days[position]
        val selectedDate = handleSelectedDate?.invoke()?.date
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
        val selectedDate = handleSelectedDate?.invoke()?.date

        val oldDatePosition = days.indexOfFirst { it?.date == selectedDate }
        val newDatePosition = days.indexOfFirst { it?.date == date }

        notifyItemChanged(oldDatePosition)
        notifyItemChanged(newDatePosition)
    }

    /**
     * Set handle selected date.
     */
    fun handleSelectedDate(handleSelectedDate: () -> SelectedDate) {
        this.handleSelectedDate = handleSelectedDate
    }
}