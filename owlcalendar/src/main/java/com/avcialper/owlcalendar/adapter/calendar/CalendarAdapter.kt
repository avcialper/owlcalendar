package com.avcialper.owlcalendar.adapter.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import com.avcialper.jdatetime.model.JDate
import com.avcialper.owlcalendar.adapter.BaseAdapter
import com.avcialper.owlcalendar.data.models.CalendarData
import com.avcialper.owlcalendar.data.models.LineDate
import com.avcialper.owlcalendar.data.models.MarkedDay
import com.avcialper.owlcalendar.data.models.MonthAndYear
import com.avcialper.owlcalendar.data.models.SelectedDate
import com.avcialper.owlcalendar.data.models.findEndIndex
import com.avcialper.owlcalendar.data.models.findIndex
import com.avcialper.owlcalendar.data.models.findStartIndex
import com.avcialper.owlcalendar.databinding.CalendarBinding

internal class CalendarAdapter(
    dateLists: List<MonthAndYear>,
    private val calendarData: CalendarData,
    private val onDayClickListener: (JDate) -> Unit
) : BaseAdapter<CalendarViewHolder>() {

    init {
        CalendarData.setOnMarkedDayAddedListener(calendarData, ::onMarkedDayAddedListener)
        CalendarData.setOnLineDateChangeListener(calendarData, ::onLineDateChangeListener)
    }

    private val months: MutableList<MonthAndYear> = dateLists.toMutableList()
    private var clickedDatePosition: Int = 0

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
        holder.bind(date, calendarData) { jDate ->
            clickedDatePosition = date.findIndex(months)
            handleDayClick(jDate)
            onDayClickListener.invoke(jDate)
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

    /**
     * Handle click of the day.
     * @param jDate Clicked day instance
     */
    override fun handleDayClick(jDate: JDate) {
        val oldPosition = calendarData.selectedDate.calendarPosition

        if (oldPosition != clickedDatePosition) {
            notifyItemChanged(oldPosition)
            notifyItemChanged(clickedDatePosition)
        }

        val selectedDate = SelectedDate(
            jDate.year,
            jDate.month,
            jDate.dayOfMonth,
            clickedDatePosition
        )

        CalendarData.updateSelectedDate(calendarData, selectedDate)
    }

    private fun onMarkedDayAddedListener(markedDay: MarkedDay) {
        val selectedDateCalendarPosition = calendarData.selectedDate.calendarPosition
        val markedDayMonthIndex = months.findIndex(markedDay)

        if (selectedDateCalendarPosition == markedDayMonthIndex)
            CalendarData.onDayUpdate(calendarData, markedDay)
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