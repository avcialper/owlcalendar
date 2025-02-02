package com.avcialper.owlcalendar.helper

import androidx.recyclerview.widget.RecyclerView

internal class CalendarScrollListener(
    private val onPositionChanged: () -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        // When the recycler view is scrolled, update the snap position to update the calendar.
        onPositionChanged()
    }
}