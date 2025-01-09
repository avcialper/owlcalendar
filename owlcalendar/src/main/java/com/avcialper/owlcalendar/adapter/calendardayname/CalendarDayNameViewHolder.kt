package com.avcialper.owlcalendar.adapter.calendardayname

import androidx.recyclerview.widget.RecyclerView
import com.avcialper.owlcalendar.databinding.CalendarDayNameBinding

class CalendarDayNameViewHolder(
    private val binding: CalendarDayNameBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(dayName: String) {
        binding.tvDayName.text = dayName
    }

}