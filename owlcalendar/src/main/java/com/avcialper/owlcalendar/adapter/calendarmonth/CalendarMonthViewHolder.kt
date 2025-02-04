package com.avcialper.owlcalendar.adapter.calendarmonth

import android.graphics.drawable.Drawable
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

    // Text colors
    private val selectedTextColor = ContextCompat.getColor(context, R.color.orange)
    private val defaultTextColor = root.currentTextColor

    private val markedDayDrawable = getDrawable(R.drawable.day_marked)?.mutate() as GradientDrawable
    private val markedDayCornerRadius = markedDayDrawable.cornerRadius

    private var lineDrawable: Drawable? = null
    private val lineStartDrawable = getDrawable(R.drawable.day_linear_start)
    private val lineEndDrawable = getDrawable(R.drawable.day_linear_end)
    private val lineInRangeDrawable = getDrawable(R.drawable.day_linear_in_range)

    fun bind(
        day: JDayOfMonth,
        isSelected: Boolean,
        calendarData: CalendarData,
        onDayClickListener: (JDayOfMonth) -> Unit
    ) {
        setTextColor(day)
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
    private fun setTextColor(day: JDayOfMonth) {
        val isToday = day.isToday()
        val textColor = if (isToday) selectedTextColor else defaultTextColor
        root.setTextColor(textColor)
    }

    /**
     * Set background of the day. If it is selected, set orange background.
     * @param isSelected Day is selected or not
     */
    private fun setBackground(isSelected: Boolean, date: JDayOfMonth, calendarData: CalendarData) {
        val markedDay = CalendarData.findMarkedDay(calendarData, date)
        val lineDate = calendarData.lineDate

        if (lineDate != null) {
            lineDrawable = when {
                lineDate.isStart(date) -> lineStartDrawable
                lineDate.isEnd(date) -> lineEndDrawable
                lineDate.isInRange(date) -> lineInRangeDrawable
                else -> null
            }
        }

        if (markedDay != null) {
            markedDayDrawable.setColor(markedDay.color)
            markedDayDrawable.cornerRadius = markedDayCornerRadius
        }

        val drawable = when {
            isSelected -> getDrawable(R.drawable.day_focused)
            markedDay != null -> markedDayDrawable
            lineDrawable != null -> lineDrawable
            else -> null
        }

        root.background = drawable
    }

    private fun getDrawable(id: Int): Drawable? = ContextCompat.getDrawable(context, id)
}
