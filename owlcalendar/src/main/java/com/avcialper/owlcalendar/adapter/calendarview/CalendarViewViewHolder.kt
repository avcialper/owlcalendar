package com.avcialper.owlcalendar.adapter.calendarview

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.jdatetime.model.JDayOfMonth
import com.avcialper.owlcalendar.R
import com.avcialper.owlcalendar.databinding.CalendarDayBinding
import com.avcialper.owlcalendar.util.extensions.convertToString

class CalendarViewViewHolder(
    binding: CalendarDayBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val root = binding.root

    // Text colors
    private val selectedTextColor = ContextCompat.getColor(root.context, R.color.orange)
    private val defaultTextColor = root.currentTextColor

    fun bind() {}

    fun bind(day: JDayOfMonth, isSelected: Boolean, onDayClickListener: (JDayOfMonth) -> Unit) {
        setTextColor(day)
        setBackground(isSelected)

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
    private fun setBackground(isSelected: Boolean) {
        val drawable = if (isSelected) R.drawable.calendar_focused else R.drawable.calendar_default
        root.setBackgroundResource(drawable)
    }

}
