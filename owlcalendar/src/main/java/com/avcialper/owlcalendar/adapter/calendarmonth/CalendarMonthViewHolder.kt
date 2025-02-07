package com.avcialper.owlcalendar.adapter.calendarmonth

import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.owlcalendar.R
import com.avcialper.owlcalendar.data.models.CalendarData
import com.avcialper.owlcalendar.data.models.Date
import com.avcialper.owlcalendar.databinding.CalendarDayBinding
import com.avcialper.owlcalendar.util.constants.CalendarMode
import com.avcialper.owlcalendar.util.extensions.convertToString

internal class CalendarMonthViewHolder(
    binding: CalendarDayBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val root = binding.root
    private val context = root.context

    private val defaultTextColor = root.currentTextColor
    private val unselectableTextColor = ContextCompat.getColor(context, R.color.grey)

    private val selectedDrawable = getDrawableAsGradientDrawable(R.drawable.day_focused)
    private val markedDayDrawable = getDrawableAsGradientDrawable(R.drawable.day_marked)

    private var lineDrawable: GradientDrawable? = null
    private val lineStartDrawable = getDrawableAsGradientDrawable(R.drawable.day_linear_start)
    private val lineEndDrawable = getDrawableAsGradientDrawable(R.drawable.day_linear_end)
    private val lineInRangeDrawable = getDrawableAsGradientDrawable(R.drawable.day_linear_in_range)

    fun bind(
        day: Date,
        calendarData: CalendarData,
        onDayClickListener: (Date) -> Unit
    ) {
        setTextColor(day, calendarData)
        setBackground(day, calendarData)
        setClickBehaviour(day, calendarData)

        val dayOfMonth = day.dayOfMonth.convertToString()
        root.apply {
            text = dayOfMonth
            setOnClickListener {
                onDayClickListener.invoke(day)
            }
        }

    }

    /**
     * Set click behaviour of the day.
     * @param day [Date] object
     * @param calendarData [CalendarData] object
     */
    private fun setClickBehaviour(day: Date, calendarData: CalendarData) {
        val isBeforeAndUnselectable = isBeforeTodayAndUnselectable(day, calendarData)

        if (isBeforeAndUnselectable)
            root.isEnabled = false
    }

    /**
     * Set text color of the day. If it is today, set orange color.
     * @param day [Date] object
     * @param calendarData [CalendarData] object
     */
    private fun setTextColor(day: Date, calendarData: CalendarData) {
        val todayTextColor = calendarData.todayTextColor
        val defaultTextColor =
            if (calendarData.dayTextColor == 0) defaultTextColor else calendarData.dayTextColor

        val isToday = day.isToday()
        val isBeforeAndUnselectable = isBeforeTodayAndUnselectable(day, calendarData)

        val textColor =
            if (isToday) todayTextColor else if (isBeforeAndUnselectable) unselectableTextColor else defaultTextColor
        root.setTextColor(textColor)
    }

    /**
     * Set background of the day. If it is selected, set orange background.
     * @param day [Date] object
     * @param calendarData [CalendarData] object
     */
    private fun setBackground(day: Date, calendarData: CalendarData) {
        val isSelected = calendarData.selectedDate.isEqual(day)

        val markedDay = CalendarData.findMarkedDay(calendarData, day)
        val lineDate = calendarData.lineDate

        if (markedDay != null)
            markedDayDrawable.setColor(markedDay.color)

        if (lineDate != null) {
            lineDrawable = when {
                lineDate.isStart(day) -> lineStartDrawable
                lineDate.isEnd(day) -> lineEndDrawable
                lineDate.isInRange(day) -> lineInRangeDrawable
                else -> null
            }
            lineDrawable?.setColor(lineDate.color)
        }

        val selectedBackgroundColor = calendarData.selectedDateBackgroundColor
        selectedDrawable.setColor(selectedBackgroundColor)

        val drawable = when {
            isSelected -> selectedDrawable
            markedDay != null -> markedDayDrawable
            lineDrawable != null -> lineDrawable
            else -> null
        }

        root.background = drawable
    }

    private fun getDrawableAsGradientDrawable(id: Int): GradientDrawable {
        return ContextCompat.getDrawable(context, id)?.mutate() as GradientDrawable
    }

    private fun isBeforeTodayAndUnselectable(day: Date, calendarData: CalendarData): Boolean {
        val isBeforeToday = day.isBeforeToday()
        val isSelectable = CalendarMode.isSelectable(calendarData.calendarMode)
        return isBeforeToday && isSelectable
    }
}
