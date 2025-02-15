package com.avcialper.owlcalendar.adapter.calendardayname

import androidx.recyclerview.widget.RecyclerView
import com.avcialper.owlcalendar.databinding.CalendarDayNameBinding
import com.avcialper.owlcalendar.helper.CalendarManager.attrs

internal class CalendarDayNameViewHolder(
    private val binding: CalendarDayNameBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val defaultTextColor = binding.root.currentTextColor

    fun bind(dayName: String) {
        binding.tvDayName.apply {
            text = dayName

            val calendarDayNameTextColor = attrs.dayNameTextColor
            val dayNameTextColor =
                if (calendarDayNameTextColor == 0) defaultTextColor else calendarDayNameTextColor
            setTextColor(dayNameTextColor)
        }
    }

}