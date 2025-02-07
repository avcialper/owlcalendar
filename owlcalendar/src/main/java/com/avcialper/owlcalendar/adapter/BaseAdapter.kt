package com.avcialper.owlcalendar.adapter

import androidx.recyclerview.widget.RecyclerView
import com.avcialper.owlcalendar.data.models.Date

internal abstract class BaseAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    abstract fun handleDayClick(date: Date)

}