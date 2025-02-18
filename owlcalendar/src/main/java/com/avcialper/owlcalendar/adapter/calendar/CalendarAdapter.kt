package com.avcialper.owlcalendar.adapter.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.owlcalendar.data.models.LineDate
import com.avcialper.owlcalendar.data.models.MarkedDay
import com.avcialper.owlcalendar.data.models.YearAndMonth
import com.avcialper.owlcalendar.data.models.findEndIndex
import com.avcialper.owlcalendar.data.models.findIndex
import com.avcialper.owlcalendar.data.models.findStartIndex
import com.avcialper.owlcalendar.databinding.CalendarBinding
import com.avcialper.owlcalendar.helper.CalendarManager
import com.avcialper.owlcalendar.helper.CalendarManager.data

internal class CalendarAdapter(dateLists: List<YearAndMonth>) :
    RecyclerView.Adapter<CalendarViewHolder>() {

    init {
        CalendarManager.setOnMarkedDayAddedListener(::onMarkedDayAddedListener)
        CalendarManager.setOnLineDateChangeListener(::onLineDateChangeListener)
    }

    private val months: MutableList<YearAndMonth> = dateLists.toMutableList()

    /**
     * First item of the list. It change automatically.
     */
    val firstItem: YearAndMonth
        get() = months.first()

    /**
     * Last item of the list. It change automatically.
     */
    val lastItem: YearAndMonth
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
     * @param yearAndMonth new item
     */
    fun addItemToStart(yearAndMonth: YearAndMonth) {
        months.add(0, yearAndMonth)
        notifyItemInserted(0)
    }

    /**
     * Add new item to the end of the list.
     * @param yearAndMonth new item
     */
    fun addItemToEnd(yearAndMonth: YearAndMonth) {
        months.add(yearAndMonth)
        notifyItemInserted(months.size - 1)
    }

    /**
     * Update date list when the start date is changed.
     * @param dateList new date list
     */
    fun updateDateList(dateList: List<YearAndMonth>) {
        months.clear()
        months.addAll(dateList)
        for (i in months.indices)
            notifyItemChanged(i)
    }

    private fun onMarkedDayAddedListener(markedDay: MarkedDay) {
        val selectedDateCalendarPosition = data.selectedDate.calendarPosition
        val markedDayMonthIndex = months.findIndex(markedDay)

        if (selectedDateCalendarPosition == markedDayMonthIndex)
            CalendarManager.onDayUpdate(markedDay)
        else
            notifyItemChanged(markedDayMonthIndex)
    }

    private fun onLineDateChangeListener(lineDate: LineDate?) {
        if (lineDate == null) return

        val startIndex = months.findStartIndex(lineDate)
        var endIndex = months.findEndIndex(lineDate)

        if (endIndex == -1)
            endIndex = months.size

        for (i in startIndex..endIndex)
            notifyItemChanged(i)
    }
}