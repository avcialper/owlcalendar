package com.avcialper.owlcalendar.helper

import androidx.recyclerview.widget.RecyclerView

class CalendarScrollListener(
    private val snapHelper: CalendarSnapHelper,
    private val onPositionChanged: (Int) -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val snapPosition = snapHelper.getSnapPosition()
        // When the recycler view is scrolled, update the snap position to update the calendar.
        onPositionChanged(snapPosition)
    }
}