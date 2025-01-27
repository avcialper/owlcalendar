package com.avcialper.owlcalendar.adapter.calendardayname

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.owlcalendar.databinding.CalendarDayNameBinding

internal class CalendarDayNameAdapter(
    private val dayNames: List<String>
) : RecyclerView.Adapter<CalendarDayNameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDayNameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = CalendarDayNameBinding.inflate(layoutInflater, parent, false)
        return CalendarDayNameViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarDayNameViewHolder, position: Int) {
        val dayName = dayNames[position]
        holder.bind(dayName)
    }

    override fun getItemCount(): Int = dayNames.size
}