package com.avcialper.owlcalendar.util.constants

internal enum class CalendarMode(val value: Int) {
    NORMAL(0), SINGLE_SELECTABLE(1), RANGE_SELECTABLE(2);

    companion object {
        fun fromValue(value: Int): CalendarMode = entries.find { it.value == value } ?: NORMAL

        fun isNormal(mode: CalendarMode): Boolean = NORMAL.value == mode.value

        fun isRangeSelectable(mode: CalendarMode): Boolean = RANGE_SELECTABLE.value == mode.value

        fun isSingleSelectable(mode: CalendarMode): Boolean = SINGLE_SELECTABLE.value == mode.value

        fun isSelectable(mode: CalendarMode): Boolean {
            return when (mode) {
                SINGLE_SELECTABLE, RANGE_SELECTABLE -> true
                else -> false
            }

        }
    }
}