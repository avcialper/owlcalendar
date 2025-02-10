package com.avcialper.owlcalendar.helper

import android.content.Context
import android.content.res.TypedArray
import com.avcialper.owlcalendar.R
import com.avcialper.owlcalendar.util.constants.CalendarMode

internal class AttrManager(
    private val typedArray: TypedArray,
    private val context: Context,
    onCompleteListener: () -> Unit
) {

    private val defBackgroundColor = getColor(R.color.orange_transparent)
    private val defTodayTextColor = getColor(R.color.orange)
    private val defTextColor = 0
    private val defCalendarMode = CalendarMode.NORMAL

    /**
     * Get color from the attributes.
     * @param index Attribute index
     * @param defVal Default value
     */
    private fun getColor(index: Int, defVal: Int): Int = typedArray.getColor(index, defVal)

    /**
     * Get color from resources.
     * @param id Resource id
     */
    private fun getColor(id: Int): Int = context.getColor(id)

    /**
     * Get integer from the attributes.
     * @param index Attribute index
     * @param defVal Default value
     */
    private fun getInt(index: Int, defVal: Int): Int = typedArray.getInt(index, defVal)

    init {
        var selectedBackgroundColor = getColor(
            R.styleable.OwlCalendar_selected_date_background_color,
            defBackgroundColor
        )
        val todayTextColor = getColor(
            R.styleable.OwlCalendar_today_text_color,
            defTodayTextColor
        )
        val dayTextColor = getColor(
            R.styleable.OwlCalendar_day_text_color,
            defTextColor
        )
        val dayNameTextColor = getColor(
            R.styleable.OwlCalendar_day_name_text_color,
            defTextColor
        )
        val dateTextColor = getColor(
            R.styleable.OwlCalendar_date_text_color,
            defTextColor
        )
        val lineBackgroundColor = getColor(
            R.styleable.OwlCalendar_line_background_color,
            defBackgroundColor
        )
        val calendarModeValue = getInt(R.styleable.OwlCalendar_mode, defCalendarMode.value)
        val calendarMode = CalendarMode.fromValue(calendarModeValue)

        if (CalendarMode.isRangeSelectable(calendarMode) && lineBackgroundColor != selectedBackgroundColor)
            selectedBackgroundColor = lineBackgroundColor

        CalendarManager.setAttrs(
            selectedBackgroundColor,
            todayTextColor,
            dayTextColor,
            dayNameTextColor,
            dateTextColor,
            lineBackgroundColor,
            calendarMode
        )

        onCompleteListener.invoke()
    }

}