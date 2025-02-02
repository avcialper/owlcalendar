package com.avcialper.owlcalendar.helper

import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.avcialper.owlcalendar.util.constants.CalendarValues

internal class CalendarSnapHelper : PagerSnapHelper() {

    private var recyclerView: RecyclerView? = null
    private var currentPosition = 1

    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        super.attachToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        if (velocityX > 0)
            currentPosition++
        else
            currentPosition--

        CalendarValues.calendarPosition = currentPosition

        return currentPosition
    }

    /**
     * Set current position to 1.
     */
    fun addedNewItemOnStart() {
        CalendarValues.calendarPosition = 1
        currentPosition = 1
    }

}