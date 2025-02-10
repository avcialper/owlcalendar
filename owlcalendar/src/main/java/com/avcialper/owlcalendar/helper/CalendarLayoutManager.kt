package com.avcialper.owlcalendar.helper

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

internal class CalendarLayoutManager(context: Context, orientation: Int) :
    GridLayoutManager(context, 7, orientation, false) {

    init {
        // Set span size lookup
        spanSizeLookup = CalendarSpanSize()
    }

    // Close vertical scrolling
    override fun canScrollVertically(): Boolean = false

    // Close horizontal scrolling
    override fun canScrollHorizontally(): Boolean = false

}

private class CalendarSpanSize : GridLayoutManager.SpanSizeLookup() {
    // Set all items span size to 1
    override fun getSpanSize(position: Int): Int = 1
}