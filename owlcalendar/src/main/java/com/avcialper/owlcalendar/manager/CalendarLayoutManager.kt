package com.avcialper.owlcalendar.manager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class CalendarLayoutManager(
    private val context: Context
) : GridLayoutManager(context, 7, VERTICAL, false) {

    init {
        // Set span size lookup
        spanSizeLookup = CalendarSpanSize()
    }

    // Close vertical scrolling
    override fun canScrollVertically(): Boolean {
        return false
    }

}

private class CalendarSpanSize : GridLayoutManager.SpanSizeLookup() {
    // Set all items span size to 1
    override fun getSpanSize(position: Int): Int {
        return 1
    }
}