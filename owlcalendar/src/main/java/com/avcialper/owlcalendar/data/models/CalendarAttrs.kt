package com.avcialper.owlcalendar.data.models

import com.avcialper.owlcalendar.util.constants.CalendarMode
import kotlinx.coroutines.flow.MutableStateFlow

internal class CalendarAttrs {

    private val _data = MutableStateFlow(this)
    val data = _data.value

    var selectedDateBackgroundColor: Int = 0
    var todayTextColor: Int = 0
    var dayTextColor: Int = 0
    var dayNameTextColor: Int = 0
    var dateTextColor: Int = 0
    var lineBackgroundColor: Int = 0
    var calendarMode: CalendarMode = CalendarMode.NORMAL
    var pickerButtonTextColor: Int = 0
    var pickerButtonText: String = ""
    var pickerButtonBackgroundColor: Int = 0
    var pickerBackgroundColor: Int = 0

    fun update(attrs: CalendarAttrs) {
        selectedDateBackgroundColor = attrs.selectedDateBackgroundColor
        todayTextColor = attrs.todayTextColor
        dayTextColor = attrs.dayTextColor
        dayNameTextColor = attrs.dayNameTextColor
        dateTextColor = attrs.dateTextColor
        lineBackgroundColor = attrs.lineBackgroundColor
        calendarMode = attrs.calendarMode
        pickerButtonTextColor = attrs.pickerButtonTextColor
        pickerButtonText = attrs.pickerButtonText
        pickerButtonBackgroundColor = attrs.pickerButtonBackgroundColor
        pickerBackgroundColor = attrs.pickerBackgroundColor
    }
}