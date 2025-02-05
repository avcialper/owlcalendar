package com.avcialper.owlcalendar.adapter.calendarmonth

import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.jdatetime.model.JDayOfMonth
import com.avcialper.owlcalendar.R
import com.avcialper.owlcalendar.data.models.CalendarData
import com.avcialper.owlcalendar.databinding.CalendarDayBinding
import com.avcialper.owlcalendar.util.extensions.convertToString

internal class CalendarMonthViewHolder(
    binding: CalendarDayBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val root = binding.root
    private val context = root.context

    private val defaultTextColor = root.currentTextColor

    private val selectedDrawable = getDrawableAsGradientDrawable(R.drawable.day_focused)
    private val markedDayDrawable = getDrawableAsGradientDrawable(R.drawable.day_marked)

    private var lineDrawable: GradientDrawable? = null
    private val lineStartDrawable = getDrawableAsGradientDrawable(R.drawable.day_linear_start)
    private val lineEndDrawable = getDrawableAsGradientDrawable(R.drawable.day_linear_end)
    private val lineInRangeDrawable = getDrawableAsGradientDrawable(R.drawable.day_linear_in_range)

    fun bind(
        day: JDayOfMonth,
        isSelected: Boolean,
        calendarData: CalendarData,
        onDayClickListener: (JDayOfMonth) -> Unit
    ) {
        setTextColor(day, calendarData)
        setBackground(isSelected, day, calendarData)

        val dayOfMonth = day.dayOfMonth.convertToString()
        root.apply {
            text = dayOfMonth
            setOnClickListener {
                onDayClickListener.invoke(day)
            }
        }

    }

    /**
     * Set text color of the day. If it is today, set orange color.
     * @param day [JDayOfMonth] object
     */
    private fun setTextColor(day: JDayOfMonth, calendarData: CalendarData) {
        val todayTextColor = calendarData.todayTextColor
        val defaultTextColor =
            if (calendarData.dayTextColor == 0) defaultTextColor else calendarData.dayTextColor

        val isToday = day.isToday()
        val textColor = if (isToday) todayTextColor else defaultTextColor
        root.setTextColor(textColor)
    }

    /**
     * Set background of the day. If it is selected, set orange background.
     * @param isSelected Day is selected or not
     */
    private fun setBackground(isSelected: Boolean, date: JDayOfMonth, calendarData: CalendarData) {
        val markedDay = CalendarData.findMarkedDay(calendarData, date)
        val lineDate = calendarData.lineDate

        if (markedDay != null)
            markedDayDrawable.setColor(markedDay.color)

        if (lineDate != null) {
            lineDrawable = when {
                lineDate.isStart(date) -> lineStartDrawable
                lineDate.isEnd(date) -> lineEndDrawable
                lineDate.isInRange(date) -> lineInRangeDrawable
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
}
