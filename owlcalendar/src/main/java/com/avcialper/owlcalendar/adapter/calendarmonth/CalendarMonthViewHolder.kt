package com.avcialper.owlcalendar.adapter.calendarmonth

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.jdatetime.model.JDayOfMonth
import com.avcialper.owlcalendar.R
import com.avcialper.owlcalendar.databinding.CalendarDayBinding
import com.avcialper.owlcalendar.util.constants.OwlCalendarValues
import com.avcialper.owlcalendar.util.extensions.convertToString

internal class CalendarMonthViewHolder(
    binding: CalendarDayBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val root = binding.root

    // Text colors
    private val selectedTextColor = ContextCompat.getColor(root.context, R.color.orange)
    private val defaultTextColor = root.currentTextColor

    fun bind(day: JDayOfMonth, isSelected: Boolean, onDayClickListener: (JDayOfMonth) -> Unit) {
        setTextColor(day)
        setBackground(isSelected, day.date)

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
    private fun setBackground(isSelected: Boolean, date: String) {
        val markedDay = OwlCalendarValues.findMarkedDay(date)

        val markedDayDrawable = getDrawable(R.drawable.day_marked)?.mutate() as GradientDrawable
        val cornerRadius = markedDayDrawable.cornerRadius


        if (markedDay != null) {
            markedDayDrawable.setColor(markedDay.color)
            markedDayDrawable.cornerRadius = cornerRadius
        }

        val drawable = when {
            isSelected -> getDrawable(R.drawable.day_focused)
            markedDay != null -> markedDayDrawable
            else -> null
        }

        root.background = drawable
    }

    private fun getDrawable(id: Int): Drawable? = ContextCompat.getDrawable(root.context, id)

}
