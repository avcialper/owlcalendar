package com.avcialper.owlcalendar.helper

import android.content.Context
import android.content.res.TypedArray
import com.avcialper.owlcalendar.R
import com.avcialper.owlcalendar.data.models.CalendarAttrs
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
    private val black = getColor(R.color.black)
    private val orange = getColor(R.color.orange)

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

    /**
     * Get string from the attributes.
     * @param index Attribute index
     * @param defVal Default value
     */
    private fun getString(index: Int, defVal: String): String =
        typedArray.getString(index) ?: defVal

    /**
     * Get string from resources.
     * @param id Resource id
     */
    private fun getString(id: Int): String = context.getString(id)

    init {
        val selectedBackgroundColor = getColor(
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
        val pickerButtonTextColor = getColor(
            R.styleable.OwlCalendar_picker_button_text_color,
            black
        )
        val pickerButtonText = getString(
            R.styleable.OwlCalendar_picker_button_text,
            getString(R.string.confirm)
        )
        val pickerButtonBackgroundColor = getColor(
            R.styleable.OwlCalendar_picker_button_background_color,
            orange
        )

        val pickerBackgroundColor = getColor(
            R.styleable.OwlCalendar_picker_background_color,
            0
        )

        val calendarModeValue = getInt(R.styleable.OwlCalendar_mode, defCalendarMode.value)
        val calendarMode = CalendarMode.fromValue(calendarModeValue)

        val attrs = CalendarAttrs().apply {
            this.selectedDateBackgroundColor = selectedBackgroundColor
            this.todayTextColor = todayTextColor
            this.dayTextColor = dayTextColor
            this.dayNameTextColor = dayNameTextColor
            this.dateTextColor = dateTextColor
            this.lineBackgroundColor = lineBackgroundColor
            this.calendarMode = calendarMode
            this.pickerButtonTextColor = pickerButtonTextColor
            this.pickerButtonText = pickerButtonText
            this.pickerButtonBackgroundColor = pickerButtonBackgroundColor
            this.pickerBackgroundColor = pickerBackgroundColor
        }

        CalendarManager.attrs.update(attrs)

        onCompleteListener.invoke()
    }

}