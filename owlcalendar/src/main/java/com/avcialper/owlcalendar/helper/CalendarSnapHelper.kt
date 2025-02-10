package com.avcialper.owlcalendar.helper

import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

internal class CalendarSnapHelper : PagerSnapHelper() {

    private var recyclerView: RecyclerView? = null
    private var position: Int
        get() = CalendarManager.data.calendarPosition
        set(value) {
            CalendarManager.data.calendarPosition = value
        }

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
            position++
        else
            position--

        return position
    }

}