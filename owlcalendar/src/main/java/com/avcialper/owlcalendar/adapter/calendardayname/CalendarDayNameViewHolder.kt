package com.avcialper.owlcalendar.adapter.calendardayname

import androidx.recyclerview.widget.RecyclerView
import com.avcialper.owlcalendar.data.models.CalendarData
import com.avcialper.owlcalendar.databinding.CalendarDayNameBinding

internal class CalendarDayNameViewHolder(
    private val binding: CalendarDayNameBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val defaultTextColor = binding.root.currentTextColor

    fun bind(dayName: String, calendarData: CalendarData) {
        binding.tvDayName.apply {
            text = dayName

            val dayNameTextColor =
                if (calendarData.dayNameTextColor == 0) defaultTextColor else calendarData.dayNameTextColor
            setTextColor(dayNameTextColor)
        }
    }

}