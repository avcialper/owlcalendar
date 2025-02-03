package com.avcialper.owlcalendar.helper

import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

internal class CalendarSnapHelper(
    private val onPositionChanged: (Int) -> Unit
) : PagerSnapHelper() {

    private var recyclerView: RecyclerView? = null
    private var position = 1

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

        onPositionChanged(position)

        return position
    }

    /**
     * Set current position to 1.
     */
    fun addedNewItemOnStart() {
        onPositionChanged(1)
        position = 1
    }

}