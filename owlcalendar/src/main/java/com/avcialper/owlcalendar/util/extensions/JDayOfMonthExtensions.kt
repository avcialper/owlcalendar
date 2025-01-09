package com.avcialper.owlcalendar.util.extensions

import com.avcialper.jdatetime.model.JDayOfMonth

fun List<JDayOfMonth>.adjustStart(): List<JDayOfMonth?> {
    val firstDayIndex = this[0].dayOfWeek
    val emptyDays: MutableList<JDayOfMonth?> = (0 until firstDayIndex).map { null }.toMutableList()
    emptyDays.addAll(this)
    return emptyDays
}