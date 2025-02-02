package com.avcialper.owlcalendar.adapter

import androidx.recyclerview.widget.RecyclerView
import com.avcialper.jdatetime.model.JDayOfMonth

internal abstract class BaseAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    abstract fun handleDayClick(jDayOfMonth: JDayOfMonth)

}