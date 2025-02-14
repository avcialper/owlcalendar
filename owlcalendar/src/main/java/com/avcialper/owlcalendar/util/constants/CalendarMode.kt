package com.avcialper.owlcalendar.util.constants

enum class CalendarMode(val value: Int) {
    NORMAL(0), SINGLE_SELECTABLE(1), RANGE_SELECTABLE(2);

    fun isNormal() = this == NORMAL
    fun isSingleSelectable() = this == SINGLE_SELECTABLE
    fun isRangeSelectable() = this == RANGE_SELECTABLE
    fun isSelectable() = isRangeSelectable() || isSingleSelectable()

    companion object {
        fun fromValue(value: Int): CalendarMode = entries.find { it.value == value } ?: NORMAL
    }
}