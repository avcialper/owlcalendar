package com.avcialper.owlcalendar.adapter.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.owlcalendar.data.models.OwlDate
import com.avcialper.owlcalendar.data.models.SelectedDate
import com.avcialper.owlcalendar.databinding.CalendarViewBinding

internal class CalendarAdapter(
    dateLists: List<OwlDate>,
    private val handleSelectedDate: () -> SelectedDate,
    private val onDayClickListener: (SelectedDate) -> Unit
) : RecyclerView.Adapter<CalendarViewHolder>() {

    // Date list of the adapter
    private val dateLists: MutableList<OwlDate> = dateLists.toMutableList()

    /**
     * First item of the list. It change automatically.
     */
    val firstItem: OwlDate
        get() = dateLists.first()

    /**
     * Last item of the list. It change automatically.
     */
    val lastItem: OwlDate
        get() = dateLists.last()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = CalendarViewBinding.inflate(layoutInflater, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = dateLists[position]
        holder.bind(date, handleSelectedDate) { jDayOfMonth ->
            val selectedDate = SelectedDate(jDayOfMonth.date, position)

            handleDayClick(selectedDate)
            onDayClickListener.invoke(selectedDate)
        }
    }

    override fun getItemCount(): Int = dateLists.size

    /**
     * Add new item to the start of the list.
     * @param owlDate new item
     */
    fun addItemToStart(owlDate: OwlDate) {
        dateLists.add(0, owlDate)
        notifyItemInserted(0)
    }

    /**
     * Add new item to the end of the list.
     * @param owlDate new item
     */
    fun addItemToEnd(owlDate: OwlDate) {
        dateLists.add(owlDate)
        notifyItemInserted(dateLists.size - 1)
    }

    /**
     * Handle click of the day.
     * @param selectedDate Clicked day instance
     */
    private fun handleDayClick(selectedDate: SelectedDate) {
        val oldPosition = handleSelectedDate.invoke().calendarPosition
        val newPosition = selectedDate.calendarPosition

        notifyItemChanged(oldPosition)
        notifyItemChanged(newPosition)
    }
}