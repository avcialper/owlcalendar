package com.avcialper.owlcalendar.util.constants

internal enum class CalendarType(val value: Int) {
    NORMAL(0), SINGLE_SELECTABLE(1), RANGE_SELECTABLE(2);

    companion object {
        fun fromValue(value: Int): CalendarType = entries.find { it.value == value } ?: NORMAL
    }
}