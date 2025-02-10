package com.avcialper.owlcalendar.adapter.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.owlcalendar.data.models.LineDate
import com.avcialper.owlcalendar.data.models.MarkedDay
import com.avcialper.owlcalendar.data.models.MonthAndYear
import com.avcialper.owlcalendar.data.models.findEndIndex
import com.avcialper.owlcalendar.data.models.findIndex
import com.avcialper.owlcalendar.data.models.findStartIndex
import com.avcialper.owlcalendar.databinding.CalendarBinding
import com.avcialper.owlcalendar.helper.CalendarManager

internal class CalendarAdapter(dateLists: List<MonthAndYear>) :
    RecyclerView.Adapter<CalendarViewHolder>() {

    init {
        CalendarManager.setOnMarkedDayAddedListener(::onMarkedDayAddedListener)
        CalendarManager.setOnLineDateChangeListener(::onLineDateChangeListener)
    }

    private val months: MutableList<MonthAndYear> = dateLists.toMutableList()

    /**
     * First item of the list. It change automatically.
     */
    val firstItem: MonthAndYear
        get() = months.first()

    /**
     * Last item of the list. It change automatically.
     */
    val lastItem: MonthAndYear
        get() = months.last()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = CalendarBinding.inflate(layoutInflater, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = months[position]
        holder.bind(date) {
            val clickedDatePosition = date.findIndex(months)
            CalendarManager.handleDayClick(it, clickedDatePosition) { itemPosition ->
                notifyItemChanged(itemPosition)
            }
            CalendarManager.onDayClickListener?.invoke(it)
        }
    }

    override fun getItemCount(): Int = months.size

    /**
     * Add new item to the start of the list.
     * @param monthAndYear new item
     */
    fun addItemToStart(monthAndYear: MonthAndYear) {
        months.add(0, monthAndYear)
        notifyItemInserted(0)
    }

    /**
     * Add new item to the end of the list.
     * @param monthAndYear new item
     */
    fun addItemToEnd(monthAndYear: MonthAndYear) {
        months.add(monthAndYear)
        notifyItemInserted(months.size - 1)
    }

    private fun onMarkedDayAddedListener(markedDay: MarkedDay) {
        val selectedDateCalendarPosition = CalendarManager.data.selectedDate.calendarPosition
        val markedDayMonthIndex = months.findIndex(markedDay)

        if (selectedDateCalendarPosition == markedDayMonthIndex)
            CalendarManager.onDayUpdate(markedDay)
        else
            notifyItemChanged(markedDayMonthIndex)
    }

    private fun onLineDateChangeListener(lineDate: LineDate) {
        val startIndex = months.findStartIndex(lineDate)
        val endIndex = months.findEndIndex(lineDate)

        for (i in startIndex..endIndex)
            notifyItemChanged(i)
    }
}